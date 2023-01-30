package net.casqan.scifigame;

import name.panitz.game2d.*;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.animations.EntityAction;
import net.casqan.scifigame.core.Camera;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.core.Layers;
import net.casqan.scifigame.entities.*;
import net.casqan.scifigame.extensions.Pair;
import net.casqan.scifigame.extensions.Physics;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.dungeon.Dungeon;
import net.casqan.scifigame.gizmos.Gizmos;
import net.casqan.scifigame.input.InputManager;
import net.casqan.scifigame.sprite.*;
import net.casqan.scifigame.tilesystem.Tileset;
import net.casqan.scifigame.ui.UIComponent;
import net.casqan.scifigame.ui.UIRectangle;
import net.casqan.scifigame.ui.UIStyle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.SQLType;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.event.KeyEvent.*;

public class Game2D implements Game{
    public static HashMap<String,GameObj> PREFABS = new HashMap<>();
    public Key keyEntity;
    int width;
    int height;
    Player player;
    Camera camera;
    Font font;
    VertexInt screen;
    List<Pair<String,GameObj>> destroyQueue = new ArrayList<>();
    List<Pair<String,GameObj>> addQueue = new ArrayList<>();
    public VertexInt getScreen() {
        return screen;
    }
    static Game2D instance;
    public static Game2D getInstance() {
        return instance;
    }
    public int seed;

    HashMap<String,List<GameObj>> activeObjects;
    Set<String> damageLayers;
    private static Random random;
    public static Random Random() {return random;}
    public Tileset dungeonTileset;

    public Game2D(int width, int height, int seed){
        instance = this;
        GameTime.Init();
        activeObjects = new HashMap<>();
        activeObjects.put(Layers.L_STATICS,new ArrayList<>());
        activeObjects.put(Layers.L_ENTITIES,new ArrayList<>());
        this.width = width;
        this.height = height;
        screen = new VertexInt(width/2,height/2);
        this.seed = seed;
        random = new Random(seed);
    }

    public void SetPlayer(Player player){
        this.player = player;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }
    @Override
    public void width(int width){
        this.width = width;
        screen.x = width / 2;
    }

    @Override
    public void height(int height) {
        this.height = height;
        screen.y = height / 2;
    }

    @Override
    public Player player() {
        return player;
    }

    @Override
    public HashMap<String, List<GameObj>> goss() {
        return activeObjects;
    }

    @Override
    public void init() {
        GameTime.Init();
        instance = this;
        activeObjects.put(Layers.L_ENTITIES,new ArrayList<>());
        activeObjects.put(Layers.L_STATICS,new ArrayList<>());
        activeObjects.put(Layers.L_ENVIRONMENT,new ArrayList<>());
        activeObjects.put(Layers.L_UI,new ArrayList<>());

        VertexInt playerSize = new VertexInt(64,64);
        int scale = 4;
        
        Animation playerIdlepx = new Animation(new SpriteSheet("sprites/player/idlepx.png",
                playerSize,scale), 12,true);
        Animation playerIdlenx = new Animation(new SpriteSheet("sprites/player/idlenx.png",
                playerSize,scale), 12,true);
        Animation playerIdlepy = new Animation(new SpriteSheet("sprites/player/idlepy.png",
                playerSize,scale), 12,true);
        Animation playerIdleny = new Animation(new SpriteSheet("sprites/player/idleny.png",
                playerSize,scale), 12,true);

        Animation playerwalkpx = new Animation(new SpriteSheet("sprites/player/walkpx.png",
                playerSize,scale), 12,true);
        Animation playerwalknx = new Animation(new SpriteSheet("sprites/player/walknx.png",
                playerSize,scale), 12,true);
        Animation playerwalkpy = new Animation(new SpriteSheet("sprites/player/walkny.png",
                playerSize,scale), 12,true);
        Animation playerwalkny = new Animation(new SpriteSheet("sprites/player/walkpy.png",
                playerSize,scale), 12,true);

        Animation playerattackpx = new Animation(new SpriteSheet("sprites/player/attackpx.png",
                playerSize,scale), 12,false);
        Animation playerattacknx = new Animation(new SpriteSheet("sprites/player/attacknx.png",
                playerSize,scale), 12,false);
        Animation playerattackpy = new Animation(new SpriteSheet("sprites/player/attackny.png",
                playerSize,scale), 12,false);
        Animation playerattackny = new Animation(new SpriteSheet("sprites/player/attackpy.png",
                playerSize,scale), 12,false);

        Animation playerDeath = new Animation( new SpriteSheet("sprites/player/death.png",
                playerSize,scale), 12,false);

        var playerAnimations = new HashMap<String,Animation>();

        playerAnimations.put(EntityAction.IDLEPX,playerIdlepx);
        playerAnimations.put(EntityAction.IDLENX,playerIdlenx);
        playerAnimations.put(EntityAction.IDLEPY,playerIdlepy);
        playerAnimations.put(EntityAction.IDLENY,playerIdleny);

        playerAnimations.put(EntityAction.MOVEPX,playerwalkpx);
        playerAnimations.put(EntityAction.MOVENX,playerwalknx);
        playerAnimations.put(EntityAction.MOVEPY,playerwalkpy);
        playerAnimations.put(EntityAction.MOVENY,playerwalkny);

        playerAnimations.put(EntityAction.ATTACKPX,playerattackpx);
        playerAnimations.put(EntityAction.ATTACKNX,playerattacknx);
        playerAnimations.put(EntityAction.ATTACKPY,playerattackpy);
        playerAnimations.put(EntityAction.ATTACKNY,playerattackny);

        playerAnimations.put(EntityAction.DEATH,playerDeath);

        Player _player = new Player(playerAnimations,new Vertex(0,0),
                new Vertex(112,132),32,16,new Vertex(0,0),1,EntityAction.IDLEPX);
        _player.onDeath.Clear();
        _player.name = "player";
        _player.health = 200;
        _player.keys = 0;
        SetPlayer(_player);

        camera = new Camera();
        Camera.main = camera;

        playerattackpx.onAnimationEnd.AddListener((anim) -> _player.attacking = false);
        playerattacknx.onAnimationEnd.AddListener((anim) -> _player.attacking = false);
        playerattackpy.onAnimationEnd.AddListener((anim) -> _player.attacking = false);
        playerattackny.onAnimationEnd.AddListener((anim) -> _player.attacking = false);

        //Input Setup
        InputManager.RegisterOnKeyDown(VK_W,(key) -> player().velocity().add(new Vertex(0,-2)));
        InputManager.RegisterOnKeyUp(VK_W,(key) -> player().velocity().add(new Vertex(0,2)));

        InputManager.RegisterOnKeyDown(VK_S,(key) -> player().velocity().add(new Vertex(0,2)));
        InputManager.RegisterOnKeyUp(VK_S,(key) -> player().velocity().add(new Vertex(0,-2)));

        InputManager.RegisterOnKeyDown(VK_D,(key) -> player().velocity().add(new Vertex(2,0)));
        InputManager.RegisterOnKeyUp(VK_D,(key) -> player().velocity().add(new Vertex(-2,0)));

        InputManager.RegisterOnKeyDown(VK_A,(key) -> player().velocity().add(new Vertex(-2,0)));
        InputManager.RegisterOnKeyUp(VK_A,(key) -> player().velocity().add(new Vertex(2,0)));

        InputManager.RegisterOnKeyDown(VK_R,(key) -> Gizmos.Clear());

        damageLayers = new HashSet<>();
        damageLayers.add(Layers.L_ENTITIES);
        InputManager.RegisterOnKeyDown(VK_SPACE,(key) -> {
            if (player.attacking) return;
            System.out.println("==================ATTACK DATA==================");
            _player.Attack();
            System.out.println("Attacking!");
            Vertex size = new Vertex(64,64);
            Vertex pos = Vertex.add(player.pos(),player.anchor());

            //There is absolutely a cleaner Mathematical way to do this,
            // but I'm not going to bother implementing it, because this just works
            if (player.forward().y > 0){
                pos.y += player.height();
                pos.x -= size.x / 2f - player.width()/ 2f;
            }
            else if (player.forward.y < 0) {
                pos.y -= size.y;
                pos.x -= size.x / 2f - player.width()/ 2f;
            }
            else if (player.forward().x > 0) {
                pos.x += player.width();
                pos.y -= size.y / 2f - player.height() / 2f;
            }
            else if (player.forward().x < 0) {
                pos.x -= size.x;
                pos.y -= size.y / 2f - player.height()/ 2f;
            }


            var r = new Rect(pos,size);
            //Gizmos.Add(new Gizmo(r,Color.red));
            var col = Physics.OverlapRect(r,damageLayers);
            var damage = (int) Math.round(player().statistics.getOrDefault(Statistics.DAMAGE,10d));
            for (var go : col){
                System.out.println("Overlap with: " + go.name());
                if (go == player) continue;
                System.out.println(go instanceof Entity);
                if (!(go instanceof Entity)) continue;
                ((Entity)go).DealDamage(damage);
            }
            System.out.println("====================================");
        });

        //Enemy Setup
        Animation enemyIdlepx = new Animation(new SpriteSheet("sprites/enemy/enemy_idle_px.png",
                playerSize,scale), 12,true);
        Animation enemyIdlenx = new Animation(new SpriteSheet("sprites/enemy/enemy_idle_nx.png",
                playerSize,scale), 12,true);
        Animation enemyIdlepy = new Animation(new SpriteSheet("sprites/enemy/enemy_idle_py.png",
                playerSize,scale), 12,true);
        Animation enemyIdleny = new Animation(new SpriteSheet("sprites/enemy/enemy_idle_ny.png",
                playerSize,scale), 12,true);

        Animation enemywalkpx = new Animation(new SpriteSheet("sprites/enemy/enemy_walk_px.png",
                playerSize,scale), 12,true);
        Animation enemywalknx = new Animation(new SpriteSheet("sprites/enemy/enemy_walk_nx.png",
                playerSize,scale), 12,true);
        Animation enemywalkpy = new Animation(new SpriteSheet("sprites/enemy/enemy_walk_ny.png",
                playerSize,scale), 12,true);
        Animation enemywalkny = new Animation(new SpriteSheet("sprites/enemy/enemy_walk_py.png",
                playerSize,scale), 12,true);

        Animation enemyattackpx = new Animation(new SpriteSheet("sprites/enemy/enemy_attack_px.png",
                playerSize,scale), 12,false);
        Animation enemyattacknx = new Animation(new SpriteSheet("sprites/enemy/enemy_attack_nx.png",
                playerSize,scale), 12,false);
        Animation enemyattackpy = new Animation(new SpriteSheet("sprites/enemy/enemy_attack_ny.png",
                playerSize,scale), 12,false);
        Animation enemyattackny = new Animation(new SpriteSheet("sprites/enemy/enemy_attack_py.png",
                playerSize,scale), 12,false);

        /*Animation enemyDeath = new Animation( new SpriteSheet("sprites/enemy/death.png",
                playerSize,scale), 12,false);*/

        var enemyAnimations = new HashMap<String,Animation>();

        enemyAnimations.put(EntityAction.IDLEPX,enemyIdlepx);
        enemyAnimations.put(EntityAction.IDLENX,enemyIdlenx);
        enemyAnimations.put(EntityAction.IDLEPY,enemyIdlepy);
        enemyAnimations.put(EntityAction.IDLENY,enemyIdleny);

        enemyAnimations.put(EntityAction.MOVEPX,enemywalkpx);
        enemyAnimations.put(EntityAction.MOVENX,enemywalknx);
        enemyAnimations.put(EntityAction.MOVEPY,enemywalkpy);
        enemyAnimations.put(EntityAction.MOVENY,enemywalkny);

        enemyAnimations.put(EntityAction.ATTACKPX,enemyattackpx);
        enemyAnimations.put(EntityAction.ATTACKNX,enemyattacknx);
        enemyAnimations.put(EntityAction.ATTACKPY,enemyattackpy);
        enemyAnimations.put(EntityAction.ATTACKNY,enemyattackny);

        enemyAnimations.put(EntityAction.DEATH,playerDeath);

        Enemy enemy = new Enemy(enemyAnimations,new Vertex(0,0),
                new Vertex(112,132),32,16,new Vertex(0,0),1.5f,EntityAction.IDLEPX);
        enemy.maxHealth = 20;
        PREFABS.put("enemy",enemy);

        //!DEBUG
        InputManager.RegisterOnKeyDown(VK_E,(key) -> {
            SpawnEnemy(enemy,new Vertex(random.nextInt(500),random.nextInt(500)));
        });

        //Setup Merchant
        Animation merchantIdle = new Animation(new SpriteSheet("sprites/merchant/merchant_idle.png",
                new VertexInt(32,32),4),4,true);
        var merchantAnimations = new HashMap<String,Animation>();
        merchantAnimations.put(EntityAction.IDLEPX,merchantIdle);
        Entity merchant = new Entity(merchantAnimations,new Vertex(200,0),
                new Vertex(60,134),50,6,new Vertex(0,0),2,EntityAction.IDLEPX);
        merchant.name = "merchant";
        Instantiate(Layers.L_ENTITIES,merchant);

        //Spawn Enemies
        var list = new ArrayList<GameObj>();
        Instantiate(Layers.L_ENTITIES,player);
        //Load key Prefab
        Animation keyAnimation = new Animation(new SpriteSheet("sprites/ui/key.png",
                new VertexInt(16,16),4), 4,true);
        keyEntity = new Key(keyAnimation,Vertex.zero,Vertex.zero,64,64);

        //for (int i = 0; i < 5; i++) SpawnEnemy(player2);

        //Build Map
        dungeonTileset = new Tileset("sprites/tileset/dungeontiles.png",16,16);

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("resources/fonts/upheavtt.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT,in).deriveFont(15f);

        } catch (Exception e){
            System.out.println("Could not load font, reverting to default.");
            System.out.println(e);
        }

        GenerateDungeon();
        InputManager.RegisterOnKeyDown(VK_T, var -> GenerateDungeon());

        //Boss Setup
        var bossIdleAnimation = new Animation(new SpriteSheet("sprites/boss/boss_idle.png",
                new VertexInt(32,64),4), 4, true);
        var bossDamageAnimation = new Animation(new SpriteSheet("sprites/boss/boss_damage.png",
                new VertexInt(32,64),4), 4, false);
        var bossAnimations = new HashMap<String,Animation>();
        bossAnimations.put(EntityAction.IDLEPX,bossIdleAnimation);
        bossAnimations.put("DAMAGE",bossDamageAnimation);
        var _boss = new Boss(new Vertex(16*4*7,16*4*5),new Vertex(24,25*8),
                9*8, 3*8,bossAnimations, enemy);
        PREFABS.put("END_BOSS",_boss);
        var winScreen = new UIRectangle(new Rect(-100,-100,200,200),
                new Vertex(1,0),
                new UIStyle());
        Instantiate(Layers.L_UI,winScreen);
    }

    public void GenerateDungeon(){
        Gizmos.Clear();
        seed = new Random().nextInt();
        Dungeon dungeon = Dungeon.Generate(seed,1,1,1,1,
                16,16,0,dungeonTileset);
        activeObjects.get(Layers.L_ENVIRONMENT).clear();
        activeObjects.get(Layers.L_STATICS).clear();
        player.pos = new Vertex(0,0);

        int offset = 0;
        for (var node : dungeon.nodes){
            node.data.tileset = dungeonTileset;
            node.data.width = 16;
            node.data.height = 16;
            node.data.openDirections = Dungeon.GetCorridors(node);
            offset++;
        }
        dungeon.Root().data.BuildRoom();
        Dungeon.CreateDungeonGizmos(dungeon, 32);
    }

    public void SpawnEnemy(Enemy enemy, Vertex pos){
            Enemy instance = new Enemy(enemy,pos);
            instance.onDeath.AddListener((entity -> Destroy(entity, Layers.L_ENTITIES)));
            goss().get(Layers.L_ENTITIES).add(instance);
    }

    void _Instantiate(String layer, GameObj obj,Vertex pos){
        obj.pos().moveTo(pos);
        addQueue.add(new Pair<>(layer,obj));
    }
    public static void Instantiate(String layer, GameObj obj){
        getInstance()._Instantiate(layer,obj, obj.pos());
    }
    public static void Instantiate(String layer, GameObj obj, Vertex pos){
        getInstance()._Instantiate(layer,obj,pos);
    }

    @Override
    public void move() {
        camera.pos = Vertex.Lerp(camera.pos,Vertex.add(player.pos,player.anchor),GameTime.DeltaTime() * 3);
        for (GameObj go : goss().get(Layers.L_ENTITIES)) {
            go.move();
            //We save some Performance, by only checking for collisions with the Player
            //This is because the Player is the only Entity that needs to collide with other Entities
            if (go != player && go.touches(player)) go.onCollision(player);

            //Checks for static objects like walls or doors still needs to be done for every Entity
            for(var gostat : goss().get(Layers.L_STATICS)){
                if (go.touches(gostat)) {
                    go.onCollision(gostat);
                    gostat.onCollision(go);
                }
            }
        }
    }

    @Override
    public void doChecks() {
        if (destroyQueue.size() > 0){
            for (var go : destroyQueue){
                goss().get(go.first).remove(go.second);
            }
            destroyQueue.clear();
        }
        if (addQueue.size() > 0){
            for (var go : addQueue){
                goss().get(go.first).add(go.second);
            }
            addQueue.clear();
        }
        for (var key : goss().keySet())
            for (var go : goss().get(key))
                go.Update();
    }

    @Override
    public void keyTypedReaction(KeyEvent keyEvent) { }

    @Override
    public void keyPressedReaction(KeyEvent keyEvent) {
        InputManager.SetPressed(keyEvent.getKeyCode(),true);
    }

    @Override
    public void keyReleasedReaction(KeyEvent keyEvent) {
        InputManager.SetPressed(keyEvent.getKeyCode(),false);
    }

    @Override
    public boolean won() {
        var winScreen = new UIRectangle(new Rect(-100,-100,200,200),
                new Vertex(0.5,0.5),
                UIStyle.DEFAULT);
        Instantiate(Layers.L_UI,winScreen);
        return true;
    }

    @Override
    public boolean lost() {
        return false;
    }

    @Override
    public void Destroy(GameObj go, String layer) {
        if (destroyQueue.contains(new Pair<>(layer,go))) return;
        destroyQueue.add(new Pair<>(layer,go));
    }

    @Override
    public boolean ended() {
        return Game.super.ended();
    }

    @Override
    public void paintTo(Graphics g) {
        //Update Gametime
        GameTime.Update();

        //Draw Background
        g.setColor(Color.black);
        g.fillRect(0,0,width,height);
        g.setColor(Color.white);

        //Sort by y-Coordinate
        goss().put(Layers.L_ENTITIES,
                goss().get(Layers.L_ENTITIES).stream()                  //Convert to Stream
                .sorted(Comparator.comparingInt(GameObj::getZIndex))    //Compare Z-Index
                .collect(Collectors.toList()));                         //Convert back to List

        //Draw all StaticObjects
        for (var go : goss().get(Layers.L_ENVIRONMENT)) go.paintTo(g);
        for (var go : goss().get(Layers.L_STATICS)) go.paintTo(g);
        for (var go : goss().get(Layers.L_ENTITIES))
            try { go.paintTo(g); }
            catch (Exception e) {
                System.out.println(e);
            }

        //Draw Gizmos
        Gizmos.paintTo(g);

        //Draw UI
        PaintUI(g);
    }

    public void PaintUI(Graphics g){
        g.setFont(font);
        for (GameObj obj : activeObjects.get(Layers.L_UI)) obj.paintTo(g);
        g.setColor(Color.white);
        g.drawString(String.format("Time: %.2f", GameTime.Time()),
                width - 200,16);
        g.drawString(String.format("FPS: %.2f", 1f / GameTime.DeltaTime()),
                width - 200,32);
        g.drawString(String.format("X: %.2f", player.pos().x) + String.format(" Y: %.2f",player.pos().y),
                width - 200,48);
        g.drawString(String.format("X: %.2f", player.velocity().x) + String.format(" Y: %.2f",player.velocity().y),
                width - 200,64);
        g.drawString(String.format("Speed: %.2f", player.velocity().magnitude()),
                width - 200,80);
        g.drawString(String.format("Health: %d", player.health),
                width - 200,96);
        g.drawString(String.format("Keys: %d", player.keys),
                width - 200,112);
        g.drawString("Entities: ",
                0,16);
       for(int i = 0; i < goss().get(Layers.L_ENTITIES).size(); i++){
            var o = goss().get(Layers.L_ENTITIES).get(i);
            g.drawString(String.format(o.name() + "|" + String.format("x:%.2f",o.pos().x) +
                    " " + String.format("y:%.2f",o.pos().x) ), 20,16 * (i + 2));
        }
        g.drawString(String.format("Seed: " + seed), 4,height-4);
        g.setColor(Color.magenta);
        g.fillRect(width / 2 - 200, 8, 400,16);
        g.setColor(Color.white);
        g.drawString("Corrupted Crystal",width / 2 - 75, 40);
    }

    @Override
    public void play() {
        Game.super.play();
    }
}

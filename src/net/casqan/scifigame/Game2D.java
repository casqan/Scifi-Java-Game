package net.casqan.scifigame;

import name.panitz.game2d.*;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.extensions.EmptyGameObj;
import net.casqan.scifigame.extensions.Physics;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.gizmos.Gizmo;
import net.casqan.scifigame.gizmos.Gizmos;
import net.casqan.scifigame.input.InputManager;
import net.casqan.scifigame.input.KeyAction;
import net.casqan.scifigame.sprite.Animation;
import net.casqan.scifigame.sprite.Character;
import net.casqan.scifigame.sprite.Entity;
import net.casqan.scifigame.sprite.EntityAction;
import net.casqan.scifigame.sprite.SpriteSheet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.event.KeyEvent.*;

public class Game2D implements Game{

    //https://penusbmic.itch.io/sci-fi-character-pack-9
    int width;
    int height;
    Character player;
    Font font;

    static Game instance;
    public static Game getInstance() {
        return instance;
    }

    final Color backgroundColor = new Color(0.17f,0.17f,0.17f);
    List<List< ? extends GameObj>> activeObjects;
    ArrayList<GameObj> collidingWithPlayer = new ArrayList<>();
    //EmptyGameObj damageRect;

    Game2D(int width, int height){
        instance = this;
        GameTime.Init();
        activeObjects = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public void SetPlayer(Character player){
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
    public Character player() {
        return player;
    }

    @Override
    public List<List<? extends GameObj>> goss() {
        return activeObjects;
    }

    @Override
    public void init() {
        GameTime.Init();

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

        Character _player = new Character(playerAnimations,new Vertex(0,0),
                new Vertex(112,132),32,16,new Vertex(0,0),EntityAction.IDLEPX);
        SetPlayer(_player);
        //damageRect = new EmptyGameObj(_player.anchor(),_player.pos(),new Vertex(1,0),10,10);

        playerattackpx.onAnimationEnd.AddListener((anim) -> _player.attacking = false);
        playerattacknx.onAnimationEnd.AddListener((anim) -> _player.attacking = false);
        playerattackpy.onAnimationEnd.AddListener((anim) -> _player.attacking = false);
        playerattackny.onAnimationEnd.AddListener((anim) -> _player.attacking = false);

        InputManager.RegisterOnKeyDown(VK_W,(key) -> player().velocity().add(new Vertex(0,-2)));
        InputManager.RegisterOnKeyUp(VK_W,(key) -> player().velocity().add(new Vertex(0,2)));

        InputManager.RegisterOnKeyDown(VK_S,(key) -> player().velocity().add(new Vertex(0,2)));
        InputManager.RegisterOnKeyUp(VK_S,(key) -> player().velocity().add(new Vertex(0,-2)));

        InputManager.RegisterOnKeyDown(VK_D,(key) -> player().velocity().add(new Vertex(2,0)));
        InputManager.RegisterOnKeyUp(VK_D,(key) -> player().velocity().add(new Vertex(-2,0)));

        InputManager.RegisterOnKeyDown(VK_A,(key) -> player().velocity().add(new Vertex(-2,0)));
        InputManager.RegisterOnKeyUp(VK_A,(key) -> player().velocity().add(new Vertex(2,0)));

        InputManager.RegisterOnKeyDown(VK_R,(key) -> Gizmos.Clear());

        _player.name = "player";
        InputManager.RegisterOnKeyDown(VK_E,(key) -> {
            if (player.attacking) return;
            System.out.println("==================ATTACK DATA==================");
            _player.Attack();
            System.out.println("Attacking!");
            Vertex size = new Vertex(64,64);
            Vertex pos = Vertex.add(player.pos(),player.anchor());


            //There is absolutely a cleaner Mathematical way to do this,
            // but I'm not going to bother implementing it, because this just works
            if (player.forward().x > 0) {
                pos.x += player.width();
                pos.y -= size.y / 2f - player.height() / 2f;
            }
            else if (player.forward().x < 0) {
                pos.x -= size.x;
                pos.y -= size.y / 2f - player.height()/ 2f;
            }

            if (player.forward().y > 0){
                pos.y += player.height();
                pos.x -= size.x / 2f - player.width()/ 2f;
            }
            else if (player.forward.y < 0) {
                pos.y -= size.y;
                pos.x -= size.x / 2f - player.width()/ 2f;
            }

            var r = new Rect(pos,size);
            Gizmos.Add(new Gizmo(r,Color.red));
            var col = Physics.OverlapRect(new Vertex(player.pos()).add(player.anchor()), new Vertex(1000,1000));
            for (var go : col){
                System.out.println("Overlap with: " + go.name());
                if (go == player) continue;
                System.out.println(go instanceof Entity);
                if (!(go instanceof Entity)) continue;
                ((Entity)go).DealDamage(20);
            }
            System.out.println("====================================");
        });

        Animation merchantIdle = new Animation(new SpriteSheet("sprites/merchant/idle.png",
                new VertexInt(64,64),3),10,true);
        var merchantAnimations = new HashMap<String,Animation>();
        merchantAnimations.put(EntityAction.IDLEPX,merchantIdle);
        Entity merchant = new Entity(merchantAnimations,new Vertex(200,0),
                new Vertex(60,134),50,6,new Vertex(0,0),EntityAction.IDLEPX);
        merchant.name = "merchant";

        var list = new ArrayList<GameObj>();
        list.add(merchant);
        list.add(player);
        //list.add(damageRect);
        goss().add(list);
        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("resources/fonts/upheavtt.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT,in).deriveFont(20f);

        } catch (Exception e){
            System.out.println("Could not load font, reverting to default.");
            System.out.println(e);
        }
    }

    public void Instantiate(GameObj gameObj){

    }

    @Override
    public void move() {
        player.move();
        for (List<? extends GameObj> gos : goss()) {
            for (GameObj go : gos) {
                if (go == player) continue;
                if (go.touches(player)) collidingWithPlayer.add(go);
                go.move();
            }
        }

        if(collidingWithPlayer.size() == 0)  return;
        //Get Vertex of Player pos and colliding Object pos
        //move player away from Object
        for (GameObj go : collidingWithPlayer){
            go.pos().add(new Vertex(-go.velocity().x,-go.velocity().y));
            go.pos().add(new Vertex(player().velocity().x,player().velocity().y));
        }

        collidingWithPlayer.clear();
    }

    @Override
    public void doChecks() {

    }

    @Override
    public void keyTypedReaction(KeyEvent keyEvent) {

    }

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
        return false;
    }

    @Override
    public boolean lost() {
        return false;
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
        g.setColor(backgroundColor);
        g.fillRect(0,0,width,height);
        g.setColor(Color.white);

        //Sort by y-Coordinate
        List<? extends GameObj> sorted = goss()
                .stream()
                .flatMap(List::stream)                              //Make 2D List 1D
                .sorted(Comparator.comparingInt(GameObj::getZIndex))//Compare Z-Index
                .toList();                                          //Convert back to List

        //Draw all GameObjects
        for (var go : sorted) go.paintTo(g);

        //Setup Font
        g.setColor(Color.white);
        g.setFont(font);

        //Draw Gizmos
        //Gizmos.paintTo(g);

        //Draw UI
        g.setColor(Color.white);
        g.drawString(String.format("Time: %.2f", GameTime.Time()),
                width - 200,16);
        g.drawString(String.format("FPS: %.2f", 1f / GameTime.DeltaTime()),
                width - 200,32);
        g.drawString(String.format("X: %.2f", player.pos().x) + String.format(" Y: %.2f",player.pos().y),
                width - 200,48);
        g.drawString(String.format("X: %.2f", player.velocity().x) + String.format(" Y: %.2f",player.velocity().y),
                width - 200,64);
        g.drawString(String.format("Speed: %.2f", player.velocity().Magnitude()),
                width - 200,80);
    }

    @Override
    public void play() {
        Game.super.play();
    }
}

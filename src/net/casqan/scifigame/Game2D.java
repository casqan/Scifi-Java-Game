package net.casqan.scifigame;

import name.panitz.game2d.*;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.input.InputManager;
import net.casqan.scifigame.input.KeyAction;
import net.casqan.scifigame.sprite.Animation;
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
    GameObj player;
    Font font;
    final Color backgroundColor = new Color(0.17f,0.17f,0.17f);
    List<List< ? extends GameObj>> activeObjects;
    ArrayList<GameObj> collidingWithPlayer = new ArrayList<>();
    Rect damageRect;

    Game2D(int width, int height){
        GameTime.Init();

        activeObjects = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public void SetPlayer(GameObj player){
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
    public GameObj player() {
        return player;
    }

    @Override
    public List<List<? extends GameObj>> goss() {
        return activeObjects;
    }

    @Override
    public void init() {
        GameTime.Init();

        Animation playerIdle = new Animation(new SpriteSheet("sprites/player/idle.png",
                new VertexInt(88,30),3), 10,true);
        Animation playermovePos = new Animation(new SpriteSheet("sprites/player/shuffe(move).png",
                new VertexInt(88,30),3),10,true);
        Animation playermoveNeg = new Animation(new SpriteSheet("sprites/player/shuffe(move).png",
                new VertexInt(88,30),3),10,true);
        Animation playerattack = new Animation(new SpriteSheet("sprites/player/Sweep with VFX.png",
                new VertexInt(88,30),3),10,true);

        var playerAnimations = new HashMap<String,Animation>();
        playerAnimations.put(EntityAction.IDLE, playerIdle);
        playerAnimations.put(EntityAction.ATTACKPX, playerattack);
        playerAnimations.put(EntityAction.MOVEPX, playermovePos);
        playerAnimations.put(EntityAction.MOVENX, playermoveNeg);
        Entity entity = new Entity(playerAnimations,new Vertex(0,0),
                new Vertex(66,84),36,6,new Vertex(0,0),EntityAction.IDLE);
        SetPlayer(entity);

        damageRect = new Rect(player.pos().x + player.pos().x,
                player.pos().y + player.anchor().y, 64,64);

        InputManager.RegisterOnKeyDown(VK_W,(key) -> player().velocity().add(new Vertex(0,-2)));
        InputManager.RegisterOnKeyUp(VK_W,(key) -> player().velocity().add(new Vertex(0,2)));

        InputManager.RegisterOnKeyDown(VK_S,(key) -> player().velocity().add(new Vertex(0,2)));
        InputManager.RegisterOnKeyUp(VK_S,(key) -> player().velocity().add(new Vertex(0,-2)));

        InputManager.RegisterOnKeyDown(VK_D,(key) -> player().velocity().add(new Vertex(2,0)));
        InputManager.RegisterOnKeyUp(VK_D,(key) -> player().velocity().add(new Vertex(-2,0)));

        InputManager.RegisterOnKeyDown(VK_A,(key) -> player().velocity().add(new Vertex(-2,0)));
        InputManager.RegisterOnKeyUp(VK_A,(key) -> player().velocity().add(new Vertex(2,0)));

        InputManager.RegisterOnKeyDown(VK_E,(key) -> {
            for (var gos : goss()) for (var go : gos) {
                if (!damageRect.touches(go)) continue;
                ((Entity)go).DealDamage(10);
            }
        });

        Animation merchantIdle = new Animation(new SpriteSheet("sprites/merchant/idle.png",new VertexInt(64,64),3),10,true);
        var merchantAnimations = new HashMap<String,Animation>();
        merchantAnimations.put(EntityAction.IDLE,merchantIdle);
        Entity merchant = new Entity(merchantAnimations,new Vertex(200,0),
                new Vertex(60,134),50,6,new Vertex(0,0),EntityAction.IDLE);

        var list = new ArrayList<GameObj>();
        //list.add(entity);
        list.add(merchant);
        list.add(player);
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

        //Draw Gimzmos
        damageRect.setPos(player.pos());
        g.drawRect((int)damageRect.x(), (int)damageRect.y(), (int)damageRect.width(), (int)damageRect.height());

        //Draw UI
        g.drawString(String.format("Time: %.2f", GameTime.Time()),
                width - 200,16);
        g.drawString(String.format("FPS: %.2f", 1f / GameTime.DeltaTime()),
                width - 200,32);
        g.drawString(String.format("X: %.2f", player.pos().x) + String.format(" Y: %.2f",player.pos().y),
                width - 200,48);
        g.drawString(String.format("X: %.2f", player.velocity().x) + String.format(" Y: %.2f",player.velocity().y),
                width - 200,64);
    }

    @Override
    public void play() {
        Game.super.play();
    }
}

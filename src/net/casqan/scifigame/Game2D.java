package net.casqan.scifigame;

import name.panitz.game2d.*;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.sprite.Animation;
import net.casqan.scifigame.sprite.Entity;
import net.casqan.scifigame.sprite.EntityAction;
import net.casqan.scifigame.sprite.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_UP;

public class Game2D implements Game{

    //https://penusbmic.itch.io/sci-fi-character-pack-9
    int width;
    int height;
    GameObj player;
    Font font;
    final Color backgroundColor = new Color(0.17f,0.17f,0.17f);
    List<List< ? extends GameObj>> activeObjects;

    Game2D(int width, int height){
        GameTime.Init();

        Animation playerIdle = new Animation(new SpriteSheet("sprites/player/idle.png",new VertexInt(88,30),3), 10,true);
        Animation playermove = new Animation(new SpriteSheet("sprites/player/shuffe(move).png",new VertexInt(88,30),3),10,true);
        Animation playerattack = new Animation(new SpriteSheet("sprites/player/Sweep with VFX.png",new VertexInt(88,30),3),10,true);
        var playerAnimations = new HashMap<EntityAction,Animation>();
        playerAnimations.put(EntityAction.idle, playerIdle);
        playerAnimations.put(EntityAction.attack, playerattack);
        playerAnimations.put(EntityAction.moving, playermove);
        Entity entity = new Entity(playerAnimations,new Vertex(200,200),new Vertex(0,0),EntityAction.moving);
        this.player = entity;

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

        Animation playerIdle = new Animation(new SpriteSheet("sprites/player/idle.png",new VertexInt(88,30),3), 10,true);
        Animation playermove = new Animation(new SpriteSheet("sprites/player/shuffe(move).png",new VertexInt(88,30),3),10,true);
        Animation playerattack = new Animation(new SpriteSheet("sprites/player/Sweep with VFX.png",new VertexInt(88,30),3),10,true);
        var playerAnimations = new HashMap<EntityAction,Animation>();
        playerAnimations.put(EntityAction.idle, playerIdle);
        playerAnimations.put(EntityAction.attack, playerattack);
        playerAnimations.put(EntityAction.moving, playermove);
        Entity entity = new Entity(playerAnimations,new Vertex(0,0),new Vertex(0,0),EntityAction.moving);
        SetPlayer(entity);



        Animation merchantIdle = new Animation(new SpriteSheet("sprites/merchant/idle.png",new VertexInt(64,64),3),10,true);
        var merchantAnimations = new HashMap<EntityAction,Animation>();
        merchantAnimations.put(EntityAction.idle,merchantIdle);
        Entity merchant = new Entity(merchantAnimations,new Vertex(200,0),new Vertex(0,0),EntityAction.idle);

        var list = new ArrayList<Entity>();
        //list.add(entity);
        list.add(merchant);
        goss().add(list);

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("resources/fonts/upheavtt.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT,in).deriveFont(20f);

        } catch (Exception e){
            System.out.println("Could not load font, reverting to default.");
            System.out.println(e);
        }
    }

    @Override
    public void move() {
        Game.super.move();
    }

    @Override
    public void doChecks() {

    }

    @Override
    public void keyPressedReaction(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case VK_RIGHT -> player().velocity().add(new Vertex(1,0));
            case VK_LEFT  -> player().velocity().add(new Vertex(-1,0));
            case VK_DOWN  -> player().velocity().add(new Vertex(0,1));
            case VK_UP    -> player().velocity().add(new Vertex(0,-1));
        }
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
        GameTime.Update();
        //Game.super.paintTo(g);
        g.setColor(backgroundColor);
        g.fillRect(0,0,width,height);
        g.setColor(Color.white);
        player.paintTo(g);

        g.setColor(Color.white);
        g.setFont(font);

        g.drawString(String.format("Time: %.2f", GameTime.Time()),width - 200,16);
        g.drawString(String.format("FPS: %.2f", 1f / GameTime.DeltaTime()),width - 200,32);
        g.drawString(String.format("X: %.2f", player.pos().x) + String.format(" Y: %.2f",player.pos().y),width - 200,48);
        g.drawString(String.format("X: %.2f", player.velocity().x) + String.format(" Y: %.2f",player.velocity().y),width - 200,64);
        for (var gos:goss()) gos.forEach( go -> go.paintTo(g));
    }

    @Override
    public void play() {
        Game.super.play();
    }
}

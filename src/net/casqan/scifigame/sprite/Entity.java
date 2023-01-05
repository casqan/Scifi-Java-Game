package net.casqan.scifigame.sprite;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.core.*;
import net.casqan.scifigame.sprite.Animation;
import net.casqan.scifigame.sprite.EntityAction;

import java.awt.*;
import java.util.HashMap;

public class Entity extends AbstractGameObj {

    public Vertex pos;
    public Vertex velocity;
    public Vertex anchor;
    public int height;
    public int width;
    public int maxHealth;
    public int health;

    public Image image;
    public HashMap<String, Animation> animations;
    public String currentAction = EntityAction.IDLE;

    public int framerate;
    public int frameIndex;
    final static Color shadow = new Color(0,0,0,0.1f);

    public Entity(){
        pos = new Vertex(0,0);
        velocity = new Vertex(0,0);
        anchor = new Vertex(0,0);
        height = 0;
        width = 0;
        framerate = 0;
        frameIndex = 0;
        animations = new HashMap<>();
    }
    public Entity(HashMap<String,Animation> animations, Vertex pos, Vertex anchor, int width, int height,
                  Vertex velocity,String currentAction){

        super(pos,velocity,animations.get(currentAction).sheet.scaled.x,animations.get(currentAction).sheet.scaled.y);

        this.animations = animations;
        this.currentAction = currentAction;
        frameIndex = 0;
        image = animations.get(currentAction).GetFrame(frameIndex);
        this.pos = pos;
        this.velocity = velocity;
        this.anchor = anchor;
        this.height = height;
        this.width = width;
    }

    public Entity(Vertex p, Vertex v, double w, double h) {
        super(p, v, w, h);
    }

    public void SetVelocity(Vertex velocity) {
        this.velocity = velocity;
    }

    @Override
    public Vertex pos() {
        return pos;
    }

    @Override
    public Vertex velocity() {
        return velocity;
    }

    @Override
    public Vertex anchor() {
        return anchor;
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

    public void SetCurrentAction(String action){
        currentAction = action;
    }

    Animation CurrentAnim(){
        return animations.get(currentAction);
    }

    Image GetFrame(){
        var index = (int) (GameTime.Time() * CurrentAnim().framerate) % CurrentAnim().frameCount;
        return CurrentAnim().GetFrame(index);
    }
    public void DealDamage(int damage){
        health -= damage;
    }

    @Override
    public void paintTo(Graphics g) {
        g.drawImage(GetFrame(),(int)pos.x,(int)pos.y,null);
    }
}

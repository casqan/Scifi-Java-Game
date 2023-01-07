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
    public Vertex forward;
    public Vertex anchor;
    public int height;
    public int width;
    public int maxHealth;
    public int health;

    public HashMap<String, Animation> animations;
    String currentAction = EntityAction.IDLEPX;
    final static Color shadow = new Color(0,0,0,0.1f);

    public Entity(){
        pos = new Vertex(0,0);
        velocity = new Vertex(0,0);
        anchor = new Vertex(0,0);
        height = 0;
        width = 0;
        animations = new HashMap<>();
    }
    public Entity(HashMap<String,Animation> animations, Vertex pos, Vertex anchor, int width, int height,
                  Vertex velocity,String currentAction){

        super(pos,velocity,animations.get(currentAction).sheet.scaled.x,animations.get(currentAction).sheet.scaled.y);

        this.animations = animations;
        SetCurrentAction(currentAction);
        this.pos = pos;
        this.velocity = velocity;
        this.forward = new Vertex(velocity.x,velocity.y);
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

    public Vertex forward() {return forward;}

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
        animations.get(currentAction).Play();
    }

    Animation CurrentAnim(){
        return animations.get(currentAction);
    }

    public void DealDamage(int damage){
        System.out.println("Dealing damage to entity!");
        health -= damage;
    }

    @Override
    public void paintTo(Graphics g) {
        g.drawImage(CurrentAnim().GetCurrentFrame(), (int)pos.x,(int)pos.y,null);
        g.setColor(Color.GRAY);
        g.drawRect((int)(pos().x + anchor().x) , (int)(pos().y + anchor().y),width,height);
    }
}

package net.casqan.scifigame.entities;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.core.*;
import net.casqan.scifigame.core.Event;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.animations.EntityAction;
import net.casqan.scifigame.extensions.Pair;

import java.awt.*;
import java.util.HashMap;

public class Entity extends AbstractGameObj implements Cloneable{
    public Statistics statistics = new Statistics();

    public Vertex pos;
    public Vertex velocity;
    public Vertex forward;
    public Vertex anchor;
    public int height;
    public int width;
    public int maxHealth;
    public int health;
    public float speed;
    public Vertex screenPos;
    boolean dead = false;

    public Event<Entity> onDamage = new Event<>();
    public Event<Entity> onDeath = new Event<>();
    public Event<Pair<String,Double>> onStatChange = new Event<>();

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
                  Vertex velocity,float speed,String currentAction){

        super(pos,velocity,animations.get(currentAction).getSheet().getScaled().x,
                animations.get(currentAction).getSheet().getScaled().y);
        this.animations = animations;
        SetCurrentAction(currentAction);
        this.pos = pos;
        this.velocity = velocity;
        this.forward = new Vertex(velocity.x,velocity.y);
        this.anchor = anchor;
        this.height = height;
        this.width = width;
        onDeath.AddListener(entity -> {
            Die();
        });
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
        if (!dead) super.move();
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

    @Override
    public void Update() {

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
        if (dead) return;
        health -= CalculateDamage(damage);
        if (health < 0) health = 0;
        onDamage.Invoke(this);
        if (health <= 0){
            onDeath.Invoke(this);
            Die();
        }
    }

    //Damage formula: damage * e^(-0.1 * armor)
    int CalculateDamage(int damage){
        if (statistics.getOrDefault(Statistics.ARMOR,0D)== 0) return damage;
        return (int) (damage * Math.pow(Math.E,-0.1d * statistics.get(Statistics.ARMOR)));
    }

    public void Die(){
        dead = true;
        SetCurrentAction(EntityAction.DEATH);
        animations.get(EntityAction.DEATH).onAnimationEnd.AddListener(
                (var) -> Game2D.getInstance().Destroy(this, Layers.L_ENTITIES));
    }

    @Override
    public void onCollision(GameObj that) {
        super.onCollision(that);
        pos().add(velocity.mult(-1));
    }

    public void Interact(){
        System.out.println("Interacting with entity!");
    }
    public void UpdateStatistic(String statistic, double value){
        statistics.put(statistic, value);
        onStatChange.Invoke(new Pair<>(statistic, value));
    }
    @Override
    public void paintTo(Graphics g) {
        screenPos = getScreenPos();
        g.drawImage(CurrentAnim().GetCurrentFrame(), (int)screenPos.x,(int)screenPos.y,null);

        //Debug UI
        if (!Game2D.debug) return;
        g.setColor(Color.GRAY);
        var anchored = Camera.WorldToScreenPosition(Vertex.add(pos(),anchor()));
        g.drawRect((int)anchored.x ,(int) anchored.y,width,height);
    }

}

package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.animations.EntityAction;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.animations.Animation;

import java.util.HashMap;

public class Enemy extends Character{
    public float attackDelay = 2;
    public double attackTime;
    public int damage = 5;

    public Enemy(HashMap<String, Animation> animations, Vertex pos, Vertex anchor, int width, int height, Vertex velocity, String currentAction) {
        super(animations, pos, anchor, width, height, velocity, currentAction);
    }
    public Enemy(Enemy og, Vertex pos){
        super(og.animations, pos, og.anchor, og.width, og.height, og.velocity, og.currentAction);
        this.maxHealth = og.maxHealth;
        this.health = og.maxHealth;
        this.onDeath = og.onDeath;
        this.damage = og.damage;
    }

    @Override
    public void move() {
        super.move();
        var v = Vertex.sub(Game2D.getInstance().player().pos,pos);
        v = v.mult(1d / v.magnitude());
        velocity = v;
    }

    @Override
    public void Colliding() {
        super.Colliding();
        if (attackTime > GameTime.Time()) return;
        attackTime = GameTime.Time() + attackDelay;
        Game2D.getInstance().player().DealDamage(damage);
    }
}

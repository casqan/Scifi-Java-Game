package net.casqan.scifigame.entities;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.animations.EntityAction;
import net.casqan.scifigame.core.Event;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.core.Layers;

import java.util.HashMap;

public class Enemy extends Character{
    public float attackDelay = 2;
    public double attackTime;
    public int damage = 5;

    public Enemy(HashMap<String, Animation> animations, Vertex pos, Vertex anchor, int width, int height, Vertex velocity,float speed, String currentAction) {
        super(animations, pos, anchor, width, height, velocity,speed, currentAction);
    }
    public Enemy(Enemy og, Vertex pos){
        super(og.animations, pos, og.anchor, og.width, og.height, og.velocity, og.speed, og.currentAction);
        this.maxHealth = og.maxHealth;
        this.statistics = (Statistics) og.statistics.clone();
        this.onDeath = new Event<>();
        onDeath.AddListener(entity -> Die());
        onDeath.AddListener(entity -> Game2D.getInstance().player()
                .AddCoins(Game2D.Random().nextInt(5) + 5));
        this.damage = og.damage;
    }
    @Override
    public void Attack(){
        if (forward().x > 0 && (!currentAction.equals(EntityAction.ATTACKPX))) SetCurrentAction(EntityAction.ATTACKPX);
        if (forward().x < 0 && (!currentAction.equals(EntityAction.ATTACKNX))) SetCurrentAction(EntityAction.ATTACKNX);
        if (forward().y > 0 && (!currentAction.equals(EntityAction.ATTACKPY))) SetCurrentAction(EntityAction.ATTACKPY);
        if (forward().y < 0 && (!currentAction.equals(EntityAction.ATTACKNY))) SetCurrentAction(EntityAction.ATTACKNY);
    }

    @Override
    public void move() {
        if (dead) return;
        var player = Game2D.getInstance().player();
        var v = Vertex.sub(Vertex.add(player.pos(),player.anchor()),Vertex.add(pos(),anchor()));
        v = v.mult(1d / v.magnitude());
        velocity = v.mult(statistics.getOrDefault(Statistics.SPEED,1D));
        super.move();
    }

    @Override
    public void Die() {
        super.Die();
    }

    @Override
    public void onCollision(GameObj other) {
        super.onCollision(other);
        if (other != Game2D.getInstance().player()) return;
        if (attackTime > GameTime.Time()) return;
        Attack();
        attackTime = GameTime.Time() + attackDelay;
        Game2D.getInstance().player().DealDamage(damage);
    }
}

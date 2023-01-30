package net.casqan.scifigame.entities;

import name.panitz.game2d.Game;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.animations.EntityAction;
import net.casqan.scifigame.core.Camera;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.core.Layers;

import java.awt.*;
import java.util.HashMap;

public class Boss extends Entity {
    final static Color AOEDAMAGECOLOR = new Color(1f,0f,0.25f,0.5f);
    final static Color AOEWARNINGCOLOR = new Color(1f,0f,0.6f,0.5f);
    double nextSpawnTime;
    float spawnDelay = 25f;
    float warningTime = 4f;
    double nextDamageTime;
    float damageDelay = .5f;
    int damage = 20;
    float attackRange = 6 * 16 * 2;
    Enemy guardian;
    int enemyCount;
    Vertex damagePos;
    Vertex indicatorPos;

    public Boss(Vertex pos,Vertex anchor, int width, int height, HashMap<String,Animation> animations, Enemy guardian) {
        super(animations,pos,anchor,width,height,Vertex.zero,0f,EntityAction.IDLEPX);
        this.guardian = guardian;
        setpos(pos);
        animations.get("DAMAGE").onAnimationEnd.AddListener((var) -> currentAction = EntityAction.IDLEPX);
        onDeath.AddListener((entity -> Game2D.getInstance().won()));
        onDeath.AddListener((entity -> Game2D.getInstance().Destroy(entity,Layers.L_ENTITIES)));
        health = 500;
    }

    @Override
    public void setpos(Vertex position) {
        super.setpos(position);
        damagePos = new Vertex(0,0);
        damagePos.add(new Vertex(width / 2f, height / 2f));
        damagePos.add(pos);
        damagePos.add(anchor);
        indicatorPos = new Vertex(damagePos);
        indicatorPos.add(new Vertex(-attackRange, -attackRange));
    }

    ///Handle with care!
    public void Update(){
        //if (true) return;
        if (enemyCount < 1 && GameTime.Time() > nextSpawnTime){
            nextSpawnTime = GameTime.Time() + spawnDelay;
            for (int i = 0; i < Game2D.Random().nextInt(5,10); i++){
                SpawnGuardian();
                enemyCount++;
            }
        }
        if (enemyCount > 0 && nextDamageTime <= GameTime.Time()){
            var dist =  Vertex.sub(Vertex.add(Game2D.getInstance().player().pos(),Game2D.getInstance().player().anchor()),damagePos).magnitude();
            if (dist < attackRange){
                Game2D.getInstance().player().DealDamage(damage);
                nextDamageTime = GameTime.Time() + damageDelay;
            }
        }
    }
    public void SpawnGuardian(){
        var spawnPos = new Vertex((Game2D.Random().nextDouble() - .5) * 16*4*6,
                (Game2D.Random().nextDouble() - .5) * 16*4*6);
        spawnPos.add(damagePos);
        spawnPos.add(guardian.anchor.mult(-1));
        var instance = new Enemy(guardian,spawnPos);
        instance.onDeath.AddListener(entity -> enemyCount--);
        Game2D.Instantiate(Layers.L_ENTITIES,instance);
    }

    public void CheckForMinionAlive(){
        if (enemyCount > 1 ) return;
        nextSpawnTime = GameTime.Time() + spawnDelay;
    }

    @Override
    public void DealDamage(int damage) {
        System.out.println("Hit Boss!");
        health -= damage;
        currentAction = "DAMAGE";
        if (health <= 0) {
            onDeath.Invoke(this);
        }
    }

    @Override
    public void paintTo(Graphics g) {
        screenPos = getScreenPos();
        var damageScreenPos = Camera.WorldToScreenPosition(indicatorPos);
        var gizmoPos = Camera.WorldToScreenPosition(Vertex.add(pos,anchor));
        if (enemyCount > 0) g.setColor(AOEDAMAGECOLOR);
        else if (nextSpawnTime - warningTime < GameTime.Time()) g.setColor(AOEWARNINGCOLOR);
        if (enemyCount > 0 || nextSpawnTime - warningTime < GameTime.Time()) {
            g.fillOval((int)damageScreenPos.x,(int)damageScreenPos.y,(int)(attackRange * 2),(int)(attackRange * 2));
        }
        g.drawImage(animations.get(currentAction).GetCurrentFrame(),(int)screenPos.x,(int)screenPos.y,null);
        g.setColor(Color.WHITE);
        g.drawString("" + health,(int)screenPos.x,(int)screenPos.y);
        g.setColor(Color.gray);
        g.drawRect((int)gizmoPos.x,(int)gizmoPos.y,width,height);
        g.drawString("Guardians: " + enemyCount,0 ,64);
        g.drawString("nextSpawnTime: " + nextSpawnTime,0 ,64 + 16);
        g.drawString("nextDamageTime: " + nextDamageTime,0 ,64 + 32);
        g.drawString("damagePos " + damagePos,0 ,64 + 48);
    }
}

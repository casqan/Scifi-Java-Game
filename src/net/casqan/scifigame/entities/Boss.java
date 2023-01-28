package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.core.GameTime;
import net.casqan.scifigame.core.GameTimer;
import net.casqan.scifigame.sprite.SpriteAnimation;
import net.casqan.scifigame.tilesystem.Wall;

import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.util.HashMap;

public class Boss extends Wall {
    final static Color AOEDAMAGECOLOR = new Color(0.6f,0.2f,0f,0.5f);
    final static Color AOEWARNINGCOLOR = new Color(0.6f,0.4f,0f,0.5f);
    Animation idleAnimation;
    double nextSpawnTime;
    float spawnDelay;
    double nextDamageTime;
    float damageDelay = .5f;
    int damage = 20;
    float attackRange = 6 * 16 * 4;
    Enemy guardian;
    int enemyCount;

    public Boss(Vertex pos, int width, int height, Animation image) {
        super(pos, width, height, null);
        idleAnimation = image;
    }

    ///Handle with care!
    public void Update(){
        if (true) return;
        if (enemyCount < 1 && GameTime.Time() > nextSpawnTime){
            for (int i = 0; i < Game2D.Random().nextInt(5,10); i++){
                //SpawnGuardian();
                enemyCount++;
            }
        }
        if (enemyCount > 1 && nextDamageTime <= GameTime.Time()){
            var dist =  Vertex.sub(Vertex.add(Game2D.getInstance().player().pos(),Game2D.getInstance().player().anchor()) ,pos).magnitude();
            //System.out.println(dist);
            if (dist < attackRange){
                Game2D.getInstance().player().DealDamage(damage);
                nextDamageTime = GameTime.Time() + damageDelay;
            }
        }
    }
    public void SpawnGuardian(){
        var spawnPos = new Vertex(Game2D.Random().nextDouble() * 6,Game2D.Random().nextDouble() * 6);
        spawnPos.add(pos);
        var instance = new Enemy(guardian,spawnPos);
        instance.onDeath.AddListener(entity -> enemyCount--);
        Game2D.Instantiate(Game2D.L_ENTITIES,instance);
    }

    public void CheckForMinionAlive(){
        if (enemyCount > 1 ) return;
        nextSpawnTime = GameTime.Time() + spawnDelay;
    }

    @Override
    public void paintTo(Graphics g) {
        screenPos = getScreenPos();
        g.setColor(AOEDAMAGECOLOR);
        g.fillOval((int)screenPos.x,(int)screenPos.y,(int) attackRange,(int)attackRange);
        g.drawImage(idleAnimation.GetCurrentFrame(),(int)screenPos.x,(int)screenPos.y,null);
    }
}

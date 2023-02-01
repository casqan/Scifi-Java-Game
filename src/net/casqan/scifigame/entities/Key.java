package net.casqan.scifigame.entities;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.animations.EntityAction;
import net.casqan.scifigame.core.Layers;

import java.util.HashMap;

public class Key extends Entity {

    public Key(){
        super();
    }
    public Key(Animation animation, Vertex pos, Vertex anchor, int width, int height){
        super(new HashMap<String, Animation>(){{put(EntityAction.IDLEPX,animation);}},
                pos,anchor,width,height,new Vertex(0,0),0,EntityAction.IDLEPX);
    }
    public Key(Key key, Vertex pos){
        super(key.animations, pos, key.anchor, key.width, key.height, key.velocity, key.speed, key.currentAction);
    }

    @Override
    public void move() { }

    @Override
    public void DealDamage(int damage) { }

    @Override
    public void onCollision(GameObj that) {
        if (that instanceof Player player){
            player.AddKeys(1);
            Game2D.getInstance().Destroy(this, Layers.L_ENTITIES);
        }
    }
}

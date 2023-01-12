package net.casqan.scifigame.entities;

import name.panitz.game2d.GameObj;
import net.casqan.scifigame.Game2D;

public class Key extends Entity {

    @Override
    public void DealDamage(int damage) { }

    @Override
    public void onCollision(GameObj that) {
        if (that instanceof Player player){
            player.keys++;
            Game2D.getInstance().destroy(this,Game2D.L_ENTITIES);
        }
    }
}

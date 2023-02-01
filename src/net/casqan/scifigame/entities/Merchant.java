package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.animations.Animation;

import java.util.HashMap;

public class Merchant extends Character{
    public Merchant(HashMap<String, Animation> animations, Vertex pos, Vertex anchor, int width, int height, Vertex velocity, float speed, String currentAction) {
        super(animations, pos, anchor, width, height, velocity, speed, currentAction);
    }

    @Override
    public void DealDamage(int damage) {
        return;
    }
}

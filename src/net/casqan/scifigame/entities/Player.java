package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.animations.Animation;

import java.util.HashMap;

public class Player extends Character{
    public Player(HashMap<String, Animation> animations,
                  Vertex pos, Vertex anchor,
                  int width, int height,
                  Vertex velocity, float speed,
                  String currentAction) {
        super(animations, pos, anchor, width, height, velocity, speed, currentAction);
    }

    public int keys;
    public int Keys(){
        return keys;
    }

    public int coins;
    public int Coins(){
        return coins;
    }
}

package net.casqan.scifigame.sprite;

import name.panitz.game2d.Vertex;

import java.util.HashMap;

public class Character extends Entity{
    public Character(HashMap<String, Animation> animations, Vertex pos, Vertex anchor, int width, int height, Vertex velocity, String currentAction) {
        super(animations, pos, anchor, width, height, velocity, currentAction);
    }
    public void Attack(){

        currentAction = EntityAction.ATTACKNX;
    }
}

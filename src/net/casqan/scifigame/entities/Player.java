package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.core.Event;
import net.casqan.scifigame.core.Layers;

import java.util.HashMap;

public class Player extends Character{
    public Vertex movementInput = new Vertex(0,0);
    public Event<Player> onKeyPickup = new Event<>();
    public Event<Player> onCoinPickup = new Event<>();

    public Player(HashMap<String, Animation> animations,
                  Vertex pos, Vertex anchor,
                  int width, int height,
                  Vertex velocity, float speed,
                  String currentAction) {
        super(animations, pos, anchor, width, height, velocity, speed, currentAction);
    }

    @Override
    public void move() {
        var movenorm = movementInput.normalized();
        velocity = movenorm.mult(statistics.getOrDefault(Statistics.SPEED,2D));
        super.move();
    }

    @Override
    public void Die() {
        Game2D.getInstance().Destroy(this, Layers.L_ENTITIES);
        Game2D.getInstance().lost();
    }

    public int keys;
    public int Keys(){
        return keys;
    }

    public int coins;
    public int Coins(){
        return coins;
    }
    public void AddKeys(int amount){
        keys += amount;
        onKeyPickup.Invoke(this);
    }
    public void AddCoins(int amount){
        coins += amount;
        onCoinPickup.Invoke(this);
    }
}

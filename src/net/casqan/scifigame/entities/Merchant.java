package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.items.Item;
import net.casqan.scifigame.ui.ShopUI;
import net.casqan.scifigame.ui.UIStyle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Merchant extends Character{
    ShopUI shopUI = null;
    List<Item> items;

    public Merchant(HashMap<String, Animation> animations, Vertex pos, Vertex anchor, int width, int height, Vertex velocity, float speed, String currentAction) {
        super(animations, pos, anchor, width, height, velocity, speed, currentAction);
    }
    public void SetItems(Collection<Item> items){
        this.items = new ArrayList<>(items);
    }
    public Collection<Item> GetItems(){
        return this.items;
    }

    @Override
    public void Interact() {
        System.out.printf("Interacting with merchant");
        if (shopUI != null && shopUI.isOpen) {
            shopUI.Close();
        }
        else {
            var style = new UIStyle();
            style.backgroundColor = new Color(0,0,0,0.5f);
            var shopUI = new ShopUI(new ArrayList<>(),new Rect(0,0,500,300),
                    new Vertex(0.5,0.5),style);
            shopUI.setItems(items);
            System.out.println(items.size());
            shopUI.Show();
            this.shopUI = shopUI;
        }
    }

    @Override
    public void DealDamage(int damage) {
        return;
    }
}

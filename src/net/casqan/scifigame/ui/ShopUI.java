package net.casqan.scifigame.ui;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.core.Layers;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.input.InputManager;
import net.casqan.scifigame.items.Item;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static java.awt.event.KeyEvent.*;

public class ShopUI extends UIComponent{
    List<Item> items = new ArrayList<>();
    List<UIComponent> children = new ArrayList<>();
    Consumer<Integer> closeFunc;
    List<Consumer<Integer>> buyFuncs = new ArrayList<>();
    public boolean isOpen = false;

    public ShopUI(List<Item> items, Rect rect, Vertex anchor, UIStyle style) {
        super(rect, anchor, style);
        this.items = items;
    }

    public void setItems(List<Item> items){
        this.items = items;
    }
    public void Show(){
        var background = new UIRectangle(new Rect(0,0,width(),height()), Vertex.half, style);
        children.add(background);
        System.out.println(items.size());
        for (int i = 0; i < items.size(); i++) {
            var w = (-items.size() / 2) + i;
            var itemUI = new ItemUI(items.get(i), new Rect( w * 150 ,-50,0,0), Vertex.half, style);
            itemUI.Show();
            children.addAll(itemUI.components);
            System.out.println(items.get(i).name);
        }

        var header = new UILabel("Shop", new Rect(0,-height() / 2 + 20,0,0), Vertex.half, style);
        children.add(header);
        var description = new UILabel("Buy items to improve your stats", new Rect(0,height() / 2,0,0),
                Vertex.half, style);
        children.add(description);
        System.out.println("ShopUI.Show()");
        for (var child : children) {
            Game2D.Instantiate(Layers.L_UI,child);
            //child.Show();
        }
        isOpen = true;
        if (closeFunc == null) closeFunc = (key) -> this.Close();
        InputManager.RegisterOnKeyDown(VK_ESCAPE,closeFunc);
        if (buyFuncs.size() == 0 || items.size() != buyFuncs.size()){
            buyFuncs.clear();
            for (int i = 0; i < items.size(); i++){
                int finalI = i;
                buyFuncs.add(key -> this.BuyItem(finalI));
            }
        }

        for (int i = 0; i < items.size(); i++){
            InputManager.RegisterOnKeyDown(VK_1 + i,buyFuncs.get(i));
        }
    }
    public void Close(){
        isOpen = false;
        for (var child : children) {
            Game2D.getInstance().Destroy(child,Layers.L_UI);
        }
        InputManager.UnregisterOnKeyDown(VK_ESCAPE, closeFunc);
        for (int i = 0; i < items.size(); i++){
            int finalI = i;
            System.out.println("Removing purchasing Item on slot: " + finalI);
            InputManager.UnregisterOnKeyDown(VK_1 + i,buyFuncs.get(i));
        }
    }

    public void BuyItem(int itemIndex){
        var _item = items.get(itemIndex);
        var _player = Game2D.getInstance().player();
        if (_player.coins > _item.price){
            _player.AddCoins(-_item.price);
            _player.ConsumeItem(_item);
            for (int i = 0; i < items.size(); i++){
                int finalI = i;
                System.out.println("Removing purchasing Item on slot: " + finalI);
                InputManager.UnregisterOnKeyDown(VK_1 + i,buyFuncs.get(i));
            }
            items.remove(itemIndex);
            Close();
        }
    }
}

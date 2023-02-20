package net.casqan.scifigame.ui;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.core.Layers;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.items.Item;

import java.util.ArrayList;

public class ItemUI extends UIComponent{
    Item item;
    public ArrayList<UIComponent> components = new ArrayList<>();
    public ItemUI(Item item, Rect rect, Vertex anchor, UIStyle style) {
        super(rect, anchor, style);
        this.item = item;
    }

    public void setItem(Item item){
        this.item = item;
    }

    //Yes, this is also horrible, I should be using a unified Child System for GameObjects, to enable and
    //Disable them and not check for size of components, but here we are, this works. I might change it later
    @Override
    public void Show(){
        if (components.size() > 0) return;
        var NameUI = new UILabel(item.name, new Rect(rect.x(),rect.y(),100,100), Vertex.half, style);
        var PriceUI = new UILabel(item.price + "", new Rect(rect.x(),rect.y() + 64,100,100), Vertex.half, style);
        String buffer = "";
        for (var key : item.statistics.keySet()){
            buffer += "+" + item.statistics.get(key) + " " + key + "\n";
        }
        var StatisticUI = new UILabel(buffer, new Rect(rect.x(),rect.y() + 16,100,100), Vertex.half,style);
        components.add(NameUI);
        components.add(StatisticUI);
        components.add(PriceUI);
    }
}

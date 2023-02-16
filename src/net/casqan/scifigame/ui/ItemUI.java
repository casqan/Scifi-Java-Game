package net.casqan.scifigame.ui;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.items.Item;

public class ItemUI extends UIComponent{
    Item item;
    public ItemUI(Item item, Rect rect, Vertex anchor, UIStyle style) {
        super(rect, anchor, style);
    }

    public void setItem(Item item){
        this.item = item;
    }
    public void Show(){
        var NameUI = new UILabel(item.name, new Rect(0,0,0,0), Vertex.zero, style);
        var DescriptionUI = new UILabel(item.description, new Rect(0,0,0,0), Vertex.zero, style);
        var PriceUI = new UILabel(item.price + "", new Rect(0,0,0,0), Vertex.zero, style);

    }
}

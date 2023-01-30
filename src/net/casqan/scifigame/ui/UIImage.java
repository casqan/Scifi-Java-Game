package net.casqan.scifigame.ui;

import name.panitz.game2d.ImageObject;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.Rect;

import java.awt.*;

public class UIImage extends UIComponent{
    Image image;

    public UIImage(Image image,Rect rect, Vertex anchor, UIStyle style) {
        super(rect, anchor, style);
        this.image = image;
    }

    @Override
    public void paintTo(Graphics g) {
        super.paintTo(g);
        g.drawImage(image,asp.x,asp.y,null);
    }
}

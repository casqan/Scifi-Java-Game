package net.casqan.scifigame.ui;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.Rect;

import java.awt.*;

public class UIRectangle extends UIComponent{

    public UIRectangle(Rect rect, Vertex anchor, UIStyle style) {
        super(rect,anchor,style);
    }

    @Override
    public void paintTo(Graphics g) {
        asp = GenerateAnchoredPosition();
        g.setColor(style.backgroundColor);
        g.fillRect(asp.x, asp.y, (int)rect.width(), (int)rect.height());
    }
}

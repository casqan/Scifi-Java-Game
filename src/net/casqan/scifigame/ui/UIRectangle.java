package net.casqan.scifigame.ui;

import java.awt.*;

public class UIRectangle extends UIComponent{
    public UIStyle style;

    @Override
    public void paintTo(Graphics g) {
        super.paintTo(g);
        g.setColor(style.backgroundColor);
        g.drawRect((int)rect.x(), (int)rect.y(), (int)rect.width(), (int)rect.height());
    }
}

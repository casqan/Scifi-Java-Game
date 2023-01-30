package net.casqan.scifigame.ui;

import name.panitz.game2d.ImageObject;

import java.awt.*;

public class UIImage extends UIComponent{
    Image image;

    @Override
    public void paintTo(Graphics g) {
        super.paintTo(g);
        g.drawImage(image,asp.x,asp.y,null);
    }
}

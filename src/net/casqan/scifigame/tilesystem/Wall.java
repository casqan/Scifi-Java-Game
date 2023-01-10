package net.casqan.scifigame.tilesystem;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.Vertex;

import java.awt.*;

public class Wall extends AbstractGameObj {

    Image image;
    public Wall(Vertex pos, int width, int height,Image image) {
        super(pos, new Vertex(0, 0), width, height);
        anchor = new Vertex(0, 0);
    }

    @Override
    public void paintTo(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)getScreenPos().x,(int)getScreenPos().y,(int)width(),(int)height());
    }
}

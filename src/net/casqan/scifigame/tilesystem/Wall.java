package net.casqan.scifigame.tilesystem;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.Vertex;

import java.awt.*;

public class Wall extends AbstractGameObj {
    public Vertex screenPos;

    Image image;
    public Wall(Vertex pos, int width, int height,Image image) {
        super(pos, new Vertex(0, 0), width, height);
        anchor = new Vertex(0, 0);
    }

    @Override
    public void Update() {

    }

    @Override
    public void paintTo(Graphics g) {
        g.setColor(Color.BLACK);
        screenPos = getScreenPos();
        g.fillRect((int)screenPos.x,(int)screenPos.y,(int)width(),(int)height());
    }
}

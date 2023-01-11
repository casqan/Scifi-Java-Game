package net.casqan.scifigame.core;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;

import java.awt.*;

public class Camera extends AbstractGameObj {
    public static Camera main;

    public static Camera Main(){
        return main;
    }
    public static Vertex WorldToScreenPosition(Vertex vertex){
        double x = vertex.x - Camera.Main().pos().x + Game2D.getInstance().getScreen().x;
        double y = vertex.y - Camera.Main().pos().y + Game2D.getInstance().getScreen().y;
        return new Vertex(x,y);
    }
    @Override
    public void paintTo(Graphics g) {
        return;
    }
}

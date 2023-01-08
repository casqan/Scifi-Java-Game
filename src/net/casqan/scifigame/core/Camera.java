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
        return Vertex.add(Vertex.sub(vertex,Camera.Main().pos()), Game2D.getInstance().getScreen());
    }
    @Override
    public void paintTo(Graphics g) {
        return;
    }
}

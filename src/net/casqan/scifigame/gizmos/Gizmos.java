package net.casqan.scifigame.gizmos;

import net.casqan.scifigame.core.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gizmos {
    static List<Gizmo> rectList = new ArrayList<>();
    public static void Add(Gizmo g){
        rectList.add(g);
    }

    public static void Clear(){
        rectList.clear();
    }
    public static void paintTo(Graphics g){
        for (var giz : rectList){
            g.setColor(giz.color);
            var sp = Camera.WorldToScreenPosition(giz.rect.pos());
            g.drawRect((int)sp.x,
                    (int)sp.y,
                    (int)giz.rect.width(),
                    (int)giz.rect.height());
        }
    }
}

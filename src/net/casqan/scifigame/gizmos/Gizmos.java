package net.casqan.scifigame.gizmos;

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
            g.drawRect((int)giz.rect.pos().x,
                    (int)giz.rect.pos().y,
                    (int)giz.rect.width(),
                    (int)giz.rect.height());
        }
    }
}

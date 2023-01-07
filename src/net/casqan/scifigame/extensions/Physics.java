package net.casqan.scifigame.extensions;

import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Physics {
    public static Collection<GameObj> OverlapRect(Vertex pos, Vertex size){
        var rect = new Rect(pos, size);
        return OverlapRect(rect);
    }
    public static Collection<GameObj> OverlapRect(Rect rect){
        var col = new ArrayList<GameObj>();
        for (var gos : Game2D.getInstance().goss()){
            for (var go : gos){
                if (rect.touches(go)) col.add(go);
            }
        }
        return col;
    }
}

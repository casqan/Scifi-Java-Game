package net.casqan.scifigame.extensions;

import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class Physics {
    public static Collection<GameObj> OverlapRect(Vertex pos, Vertex size){
        var rect = new Rect(pos, size);
        return OverlapRect(rect);
    }
    public static Collection<GameObj> OverlapRect(Rect rect){
        return OverlapRect(rect,Game2D.getInstance().goss().keySet());
    }

    public static Collection<GameObj> OverlapRect(Rect rect, Set<String> layers){
        var col = new ArrayList<GameObj>();
        for (var layer : layers){
            for (var go : Game2D.getInstance().goss().get(layer)){
                if (rect.touches(go)) col.add(go);
            }
        }
        return col;
    }
}

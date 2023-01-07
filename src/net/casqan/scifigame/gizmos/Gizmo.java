package net.casqan.scifigame.gizmos;

import net.casqan.scifigame.extensions.Rect;

import java.awt.*;

public class Gizmo {
    public Color color;
    public Rect rect;

    public Gizmo(){}
    public Gizmo(Rect rect, Color color){
        this.color = color;
        this.rect = rect;
    }
}

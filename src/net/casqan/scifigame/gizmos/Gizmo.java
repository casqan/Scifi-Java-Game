package net.casqan.scifigame.gizmos;

import net.casqan.scifigame.extensions.Rect;

import java.awt.*;

public class Gizmo {
    public Color color;
    public Rect rect;
    public boolean fill;

    public Gizmo(){}
    public Gizmo(Rect rect, Color color){
        this.color = color;
        this.rect = rect;
        this.fill = false;
    }

    public Gizmo(Rect rect, Color color, boolean fill){
        this.color = color;
        this.rect = rect;
        this.fill = fill;
    }
}

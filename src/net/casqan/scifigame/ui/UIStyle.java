package net.casqan.scifigame.ui;

import java.awt.*;

public class UIStyle {
    public static UIStyle DEFAULT = new UIStyle();

    public UIStyle(){
        color = Color.white;
        font = null;
        background = false;
        backgroundColor = Color.darkGray;
    }
    public Color color;
    public Font font;
    public boolean background;
    public Color backgroundColor;
}

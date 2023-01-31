package net.casqan.scifigame.ui;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;

import java.awt.*;

public class UILabel extends UIComponent{
    String content;
    public UILabel(String content, Rect rect, Vertex anchor, UIStyle style){
        super(rect,anchor,style);
        this.content = content;
    };
    public void SetContent(String content){ content = this.content; }
    public String Content(){ return content; }

    @Override
    public void paintTo(Graphics g) {
        asp = GenerateAnchoredPosition();
        if (style.background) {
            g.setColor(style.backgroundColor);
            g.drawRect(asp.x, asp.y, (int)rect.width(), (int)rect.height());
        }
        g.setColor(style.color);
        if(style.font != null) g.setFont(style.font);
        else g.setFont(UIStyle.DEFAULT.font);
        g.drawString(content, asp.x, asp.y);
    }
}

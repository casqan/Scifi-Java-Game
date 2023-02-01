package net.casqan.scifigame.ui;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class UILabel extends UIComponent{
    String content;
    public UILabel(String content, Rect rect, Vertex anchor, UIStyle style){
        super(rect,anchor,style);
        this.content = content;
        CalculateSize();
    };
    public void SetContent(String content){
        this.content = content;
        CalculateSize();
    }
    void CalculateSize(){
        if (style.font == null) style.font = UIStyle.DEFAULT.font;
        var context = new FontRenderContext(style.font.getTransform(),true,true);
        TextLayout layout = new TextLayout(Content(), style.font, context);
        var bounds = layout.getBounds();
        rect.dimensions.x = (int) bounds.getWidth();
        rect.dimensions.y = (int) bounds.getHeight();
    }
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

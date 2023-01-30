package net.casqan.scifigame.ui;

import java.awt.*;

public class UILabel extends UIComponent{
    String content;
    public UIStyle style;
    public UILabel() {
        super();
        style = UIStyle.DEFAULT;
    }
    public void SetContent(String content){ content = this.content; }
    public String Content(){ return content; }

    @Override
    public void paintTo(Graphics g) {
        super.paintTo(g);
        if (style.background) {
            g.setColor(style.backgroundColor);
            g.drawRect(asp.x, asp.y, (int)rect.width(), (int)rect.height());
        }
        g.setColor(style.color);
        if(style.font != null) g.setFont(style.font);
        g.drawString(content, asp.x, asp.y);
    }
}

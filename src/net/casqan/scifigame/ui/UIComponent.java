package net.casqan.scifigame.ui;

import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.TextObject;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.util.ArrayList;

public abstract class UIComponent implements GameObj {
    public Rect rect;
    public Vertex anchor;
    public VertexInt asp; // Anchored Screen Position
    public UIStyle style;
    public UIComponent(Rect rect,Vertex anchor,UIStyle style){
        this.rect = rect;
        this.asp = new VertexInt(0,0);
        this.style = style;
        this.anchor = anchor;
    }

    @Override
    public Vertex pos() {
        return rect.pos();
    }

    @Override
    public Vertex velocity() {
        return Vertex.zero;
    }

    @Override
    public Vertex anchor() {
        return anchor;
    }

    @Override
    public double width() {
        return rect.width();
    }

    @Override
    public double height() {
        return rect.height();
    }

    @Override
    public String name() {
        return "";
    }
    public void Show(){

    }

    @Override
    public void Update() {

    }

    // Der Anchor wird anders verwendet als bei normalen SpieleObjekten
    // der Anchor beschreibt die verankerte Position auf dem Bildschirm
    //     2
    //     *------------------------+     1 = (0.5,0.5)
    //     |                   3    |     2 = (0.0,0.0)
    //     |          1        *    |     3 = (0.75,0.25)
    //     |          *             |     4 = (1,1)
    //     |                        |
    //     |                        4
    //     +------------------------*
    //
    // Die '*' beschreiben jeweils einen "Anchor", so wie die Position
    // ist die Komponente mit dem Anchor 1 (0.5, 0.5) an die Mitte des
    // Bildschirms verankert und wird dort auch bleiben, der Anchor 2
    // (0,0) ist an der oberen linken Ecke verankert und wir auch dort
    // bleiben, so kann die Position von UI Komponenten auch beim Skalieren
    // des Windows vorraus gesagt werden. Die position ist dann der Offset
    // vom Anchor.
    public VertexInt GenerateAnchoredPosition(){
        int x = (int) (pos().x + anchor().x * (Game2D.getInstance().width() - width()));
        int y = (int) (pos().y + anchor().y * (Game2D.getInstance().height() - height()));
        return new VertexInt(x,y);
    }

    @Override
    public void paintTo(Graphics g) {
        asp = GenerateAnchoredPosition();
    }
}

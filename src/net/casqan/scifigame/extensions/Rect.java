package net.casqan.scifigame.extensions;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;

import javax.sound.midi.Soundbank;
import java.io.FilterOutputStream;

public class Rect {
    Vertex position;
    Vertex dimensions;

    public Vertex pos() { return position; }
    public void setPos(Vertex pos) { this.position = pos; }
    public Vertex dim() { return dimensions; }
    public void setDim(Vertex dim) { this.dimensions = dim; }

    public double x() {return pos().x;}
    public double y() {return pos().y;}
    public double width() {return dimensions.x;}
    public double height() {return dimensions.y;}

    public Rect(){}
    public Rect(double x, double y, double width, double height){
        this.position = new Vertex(x,y);
        this.dimensions = new Vertex(width,height);
    }
    public Rect(Vertex pos, double width, double height){
        this(pos.x,pos.y,width,height);
    }
    public Rect(Vertex pos, Vertex size){
        this.position = pos;
        this.dimensions = size;
    }

    public boolean isAbove(double y){return pos().y + dim().y < y;}
    public boolean isAbove(Rect rect){ return isAbove(rect.pos().y);}
    public boolean isAbove(GameObj that){return isAbove(that.pos().y + that.anchor().y);}

    public boolean isUnderneath(GameObj that){return new Rect(Vertex.add(that.pos(),that.anchor()),that.width(),that.height()).isAbove(this); }

    public boolean isLeftOf(double x){return pos().x + dim().x<x;}
    public boolean isLeftOf(GameObj that){return isLeftOf(that.pos().x + that.anchor().x);}
    public boolean isLeftOf(Rect rect) { return isLeftOf(rect.pos().x);}
    public boolean isRightOf(GameObj that){return  new Rect(Vertex.add(that.pos(),that.anchor()),that.width(),that.height()).isLeftOf(this);}

    public boolean touches(GameObj that){
        Vertex relative = Vertex.sub(pos(),that.pos());
        System.out.println(relative.Magnitude());
        System.out.println(relative);
        System.out.println("Above: " + isAbove(that) +
                "| Underneath: " + isUnderneath(that) +
                "| LeftOf: " + isLeftOf(that) +
                "| RightOf: " + isRightOf(that));
        return
                ! (    isAbove(that)  || isUnderneath(that)
                        || isLeftOf(that) || isRightOf(that)    );
    }
}

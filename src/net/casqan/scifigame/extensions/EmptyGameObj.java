package net.casqan.scifigame.extensions;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.Vertex;

import java.awt.*;

public class EmptyGameObj extends AbstractGameObj {

    public Vertex pos;
    @Override
    public Vertex pos() {return pos;}
    public Vertex velocity;
    @Override
    public Vertex velocity() {return velocity;}
    public Vertex forward;
    public Vertex anchor;
    @Override
    public Vertex anchor() {return anchor;}

    @Override
    public void Update() {

    }

    public int height;
    public int width;


    public EmptyGameObj() {
        pos = new Vertex(0, 0);
        velocity = new Vertex(0, 0);
        anchor = new Vertex(0, 0);
        forward = new Vertex(1,0);
        height = 0;
        width = 0;
    }

    public EmptyGameObj(Vertex anchor, Vertex pos,Vertex forward, int width, int height) {
        super(pos, new Vertex(0, 0), width, height);
        this.velocity = new Vertex(0, 0);
        this.pos = pos;
        this.anchor = anchor;
        this.forward = forward;
    }
    @Override
    public void move(){}

    @Override
    public void paintTo(Graphics g) {
        g.drawRect((int)(pos().x + anchor().x) , (int)(pos().y + anchor().y),width,height);
    }
}
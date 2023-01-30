package net.casqan.scifigame.extensions;

import name.panitz.game2d.Vertex;

public class VertexInt {
    public int x;
    public int y;

    public VertexInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static VertexInt Add(VertexInt position, VertexInt pos) {
        return new VertexInt(position.x + pos.x, position.y + pos.y);
    }
    public static VertexInt Sub(VertexInt position, VertexInt pos) {
        return new VertexInt(position.x - pos.x, position.y - pos.y);
    }

    public void add(Vertex that) {
        x += that.x;
        y += that.y;
    }


    public void moveTo(VertexInt that) {
        x = that.x;
        y = that.y;
    }

    public Vertex mult(double d) {
        return new Vertex(d * x, d * y);
    }
    public VertexInt multInt(double d) {
        return new VertexInt((int)(d * x), (int)(d * y));
    }

    @Override
    public String toString() {
        return "VertexInt{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VertexInt instance)) return false;
        return instance.x == x && instance.y == y;
    }
}

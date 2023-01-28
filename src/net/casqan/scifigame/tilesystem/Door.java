package net.casqan.scifigame.tilesystem;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.dungeon.Node;
import net.casqan.scifigame.dungeon.Room;

import java.awt.*;

public class Door extends Wall {
    Image image;
    Node<Room> node;

    public Door(Vertex pos, int width, int height, Image image, Node<Room> room) {
        super(pos, width, height, image);
        this.image = image;
        this.node = room;
    }

    @Override
    public void paintTo(Graphics g) {
        screenPos = getScreenPos();
        g.setColor(Color.GRAY);
        g.fillRect((int) screenPos.x, (int) screenPos.y, (int)width, (int)height);
    }

    @Override
    public void onCollision(GameObj that) {
        super.onCollision(that);
        if (that != Game2D.getInstance().player()) return;
        var player = Game2D.getInstance().player();
        if (player.Keys() < 1) return;
        node.data.BuildRoom();
        Game2D.getInstance().player().keys -= 1;
        Game2D.getInstance().Destroy(this,Game2D.L_STATICS);
    }
}

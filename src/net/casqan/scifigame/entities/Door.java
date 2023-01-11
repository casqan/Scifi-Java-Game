package net.casqan.scifigame.entities;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.generation.Node;
import net.casqan.scifigame.generation.Room;
import net.casqan.scifigame.tilesystem.Wall;

import java.awt.*;

public class Door extends Wall {
    Image image;
    Node<Room> node;

    public Door(Vertex pos, int width, int height, Image image, Node<Room> room) {
        super(pos, width, height, image);
        this.image = image;
    }

    @Override
    public void paintTo(Graphics g) {
        screenPos = getScreenPos();
        g.drawImage(image, (int)screenPos.x,(int)screenPos.y,null);
    }

    @Override
    public void onCollision(GameObj that) {
        super.onCollision(that);
        if (that != Game2D.getInstance().player()) return;
        var player = Game2D.getInstance().player();
        if (player.keys > 1)
            Game2D.getInstance().goss()
                .get(Game2D.L_STATICS).remove(this);

    }
}

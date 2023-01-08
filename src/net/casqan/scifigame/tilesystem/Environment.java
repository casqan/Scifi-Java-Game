package net.casqan.scifigame.tilesystem;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.core.Camera;

import java.awt.*;

public class Environment extends AbstractGameObj {

    public Image image;
    public Tilemap tilemap;
    public Tileset tileset;

    @Override
    public void move() { }

    public Environment(Tilemap tilemap){
        this.tilemap = tilemap;
        Rebuild();
    }
    public Environment(Tilemap tilemap,Vertex p){
        super(p,new Vertex(0,0),0,0);
        this.tilemap = tilemap;
        Rebuild();
    }

    public void Rebuild(){
        this.image = tilemap.BuildTileMap();
    }

    @Override
    public void paintTo(Graphics g) {
        Vertex screenPos = getScreenPos();
        g.drawImage(image, (int)screenPos.x, (int)screenPos.y, null);
    }
}

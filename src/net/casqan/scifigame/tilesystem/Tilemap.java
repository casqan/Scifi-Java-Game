package net.casqan.scifigame.tilesystem;

import net.casqan.scifigame.extensions.VertexInt;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tilemap {

    public Tilemap(){}
    public int scale;
    public String[][] map;
    public Tileset tileset;

    public Tilemap(Tileset tileset, String[][] map, int scale) {
        this.map = map;
        this.tileset = tileset;
        this.scale = scale;
    }

    public void SetTile(VertexInt pos, String tileId){
        map[pos.x][pos.y] = tileId;
    }
    public Image BuildTileMap(){
        var dest = new BufferedImage(
                map.length * tileset.tileWidth * scale,
                map[0].length * tileset.tileHeight * scale,
                BufferedImage.TYPE_INT_ARGB);
        var g = dest.createGraphics();
        for (int y = 0; y < map[0].length; y++){
            for (int x = 0; x < map.length; x++){
                g.drawImage(tileset.GetTile(map[x][y]).image,
                        x * tileset.tileWidth * scale, y * tileset.tileHeight * scale,
                        (x + 1) * tileset.tileWidth * scale, (y + 1) * tileset.tileHeight * scale,
                        0, 0, tileset.tileWidth, tileset.tileHeight,
                        null);
            }
        }
        g.dispose();
        dest.flush();
        return dest;
    }
}

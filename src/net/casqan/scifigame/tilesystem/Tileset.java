package net.casqan.scifigame.tilesystem;

import net.casqan.scifigame.extensions.VertexInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Tileset {

    Image sheet;
    int tileWidth;
    int tileHeight;
    public HashMap<String,Tile> tiles = new HashMap<>();

    public Tileset(){}
    public Tileset(String path, int tileWidth, int tileHeight){
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        try{
            sheet = ImageIO.read(getClass().getClassLoader().getResource("resources/" + path));
        } catch (Exception e){
            System.out.println("Could not load: " + path);
            System.out.println(e);
        }
        for (int y = 0; y < sheet.getHeight(null) / tileHeight; y++){
            for (int x = 0; x < sheet.getWidth(null) / tileWidth; x++){
                var dest = new BufferedImage(
                        tileWidth,
                        tileHeight,
                        BufferedImage.TYPE_INT_ARGB);
                var g = dest.createGraphics();
                g.drawImage(sheet,
                        0, 0, tileWidth, tileHeight,
                        x * tileWidth, y * tileHeight, (x + 1) * tileWidth, (y + 1) * tileHeight,
                        null);
                g.dispose();
                System.out.println("Tile: " + x + ", " + y);
                AddTile(x + ";" + y, new Tile(dest));
            }
        }
    }

    public void AddTile(String pos, Tile tile){
        tiles.put(pos, tile);
    }

    public Tile GetTile(String pos){
        return tiles.get(pos);
    }
    public Tile GetTile(VertexInt pos){
        return tiles.get(pos.x + ";" + pos.y);
    }

}

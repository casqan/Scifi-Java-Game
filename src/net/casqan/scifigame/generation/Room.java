package net.casqan.scifigame.generation;

import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.tilesystem.Environment;
import net.casqan.scifigame.tilesystem.Tilemap;
import net.casqan.scifigame.tilesystem.Tileset;

import java.util.Random;

public class Room {
    public VertexInt position;
    public Tilemap tilemap;
    public Environment environment;
    public Room(){}
    public Room(VertexInt position){
        this.position = position;
    }
    public void BuildRoom(int width, int height,int seed,Tileset tileset){
        tilemap = new Tilemap(tileset,null,4);
        String[][] map = new String[width][height];
        var keys = tileset.tiles.keySet().toArray();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                map[x][y] = (String) keys[Game2D.Random().nextInt(keys.length)];
            }
        }
        tilemap.map = map;
        //environment = new Environment(tilemap);
        tilemap.BuildTileMap();
        environment = new Environment(tilemap);
        environment.pos.x = position.x * width * tileset.tileWidth * tilemap.scale;
        environment.pos.y = position.y * height * tileset.tileHeight * tilemap.scale;
    }
}

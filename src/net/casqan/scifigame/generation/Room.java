package net.casqan.scifigame.generation;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.gizmos.Gizmo;
import net.casqan.scifigame.gizmos.Gizmos;
import net.casqan.scifigame.tilesystem.Environment;
import net.casqan.scifigame.tilesystem.Tilemap;
import net.casqan.scifigame.tilesystem.Tileset;
import net.casqan.scifigame.tilesystem.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    public VertexInt position;
    public Tilemap tilemap;
    public Environment environment;
    public List<Wall> walls;
    public Room(){}
    public Room(VertexInt position){
        this.position = position;
    }
    public void BuildRoom(int width, int height,int seed,Tileset tileset,List<VertexInt> openDirections){
        tilemap = new Tilemap(tileset,null,4);
        walls = new ArrayList<>();
        String[][] map = new String[width][height];
        var keys = tileset.tiles.keySet().toArray();

        boolean[][] collision = new boolean[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    collision[x][y] = true;
                }
            }
        }
        int w = width / 2 - 1;
        int h = height / 2 - 1;
        if (openDirections.contains(new VertexInt(0,1))){
            for (int x = w; x < w + 2; x++) {
                collision[x][height - 1] = false;
            }
        }
        if (openDirections.contains(new VertexInt(0,-1))){
            for (int x = w; x < w + 2; x++) {
                collision[x][0] = false;
            }
        }
        if (openDirections.contains(new VertexInt(1,0))){
            for (int y = h; y < h + 2; y++) {
                collision[width - 1][y] = false;
            }
        }
        if (openDirections.contains(new VertexInt(-1,0))){
            for (int y = h; y < h + 2; y++) {
                collision[0][y] = false;
            }
        }

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                map[x][y] = (String) keys[Game2D.Random().nextInt(keys.length)];
            }
        }

        tilemap.map = map;
        tilemap.BuildTileMap();
        environment = new Environment(tilemap);
        environment.pos.x = position.x * width * tileset.tileWidth * tilemap.scale;
        environment.pos.y = position.y * height * tileset.tileHeight * tilemap.scale;

        //Build walls
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (collision[x][y]){
                    var pos = new Vertex(environment.pos.x + x * tileset.tileWidth * tilemap.scale,
                            environment.pos.y + y * tileset.tileHeight * tilemap.scale);
                    Wall wall = new Wall(pos,tileset.tileWidth * tilemap.scale,
                            tileset.tileHeight * tilemap.scale, null);
                    walls.add(wall);
                }
            }
        }

    }
}

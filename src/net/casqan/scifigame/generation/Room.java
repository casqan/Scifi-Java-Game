package net.casqan.scifigame.generation;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.extensions.VertexInt;
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
    public void BuildRoom(int width, int height,int seed,Tileset tileset,int openDirections){
        tilemap = new Tilemap(tileset,null,4);
        walls = new ArrayList<>();
        String[][] map = new String[width][height];
        boolean[][] collision = new boolean[width][height];
        var keys = tileset.tiles.keySet().toArray();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                map[x][y] = (String) keys[Game2D.Random().nextInt(keys.length)];
                if(y == 0 || y == height-1) {
                    collision[x][y] = true;
                }else if (x == 0 || x == width-1){
                    collision[x][y] = true;
                }
            }
        }
        tilemap.map = map;
        //environment = new Environment(tilemap);
        tilemap.BuildTileMap();
        environment = new Environment(tilemap);
        environment.pos.x = position.x * width * tileset.tileWidth * tilemap.scale;
        environment.pos.y = position.y * height * tileset.tileHeight * tilemap.scale;
        for (int i = 0; i < collision.length; i++){
            for (int j = 0; j < collision[0].length; j++){
                if (collision[i][j]){
                    walls.add(new Wall(Vertex.add(environment.pos(),new Vertex(i,j).mult(tileset.tileWidth * 4)),tileset.tileWidth * 4,tileset.tileHeight * 4,null));
                }
            }
        }
    }
}

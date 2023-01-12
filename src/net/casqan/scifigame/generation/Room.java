package net.casqan.scifigame.generation;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.tilesystem.*;

import java.util.ArrayList;
import java.util.List;

import static net.casqan.scifigame.Game2D.*;

public class Room {
    public VertexInt position;
    public Tilemap tilemap;
    public Tileset tileset;
    public Environment environment;
    public int width;
    public int height;
    public int seed;
    public List<Corridor> openDirections;
    public List<Wall> walls;
    public List<Door> doors;
    public Room(){}
    public Room(VertexInt position){
        this.position = position;
    }
    public Room(VertexInt position, int width, int height,int seed,Tileset tileset,List<Corridor> openDirections){
        this.position = position;
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.tileset = tileset;
        this.openDirections = openDirections;
    }
    public void BuildRoom(){
        tilemap = new Tilemap(tileset,null,4);
        walls = new ArrayList<>();
        doors = new ArrayList<>();
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

        var up = openDirections.stream().filter(
                (c -> c.direction().equals(new VertexInt(0,-1))))
                .findFirst();
        if (up.isPresent()){
            for (int x = w; x < w + 2; x++) {
                collision[x][0] = false;
            }
            if(up.get().isExit()){
                var pos = new Vertex(
                        position.x * width * tileset.tileWidth * tilemap.scale,
                        position.y * height * tileset.tileHeight * tilemap.scale);
                pos.x += w * tileset.tileHeight * tilemap.scale;
                doors.add(new Door( pos,
                        tileset.tileWidth * tilemap.scale * 2,
                        tileset.tileHeight * tilemap.scale,
                        tileset.GetTile("0;0").image,
                        up.get().child()));
            }
        }
        var down = openDirections.stream().filter(
                c -> c.direction().equals(new VertexInt(0,1)))
                .findFirst();
        if (down.isPresent()){
            for (int x = w; x < w + 2; x++) {
                collision[x][height - 1] = false;
            }
            if (down.get().isExit()){
                var pos = new Vertex(
                        position.x * width * tileset.tileWidth * tilemap.scale,
                        position.y * height * tileset.tileHeight * tilemap.scale);
                pos.x += w * tileset.tileHeight * tilemap.scale;
                pos.y += (height - 1) * tileset.tileHeight * tilemap.scale;
                doors.add(new Door( pos,
                        tileset.tileWidth * tilemap.scale * 2,
                        tileset.tileHeight * tilemap.scale,
                        tileset.GetTile("0;0").image,
                        down.get().child()));
            }
        }
        var right = openDirections.stream().filter((
                c -> c.direction().equals(new VertexInt(1,0))))
                .findFirst();
        if (right.isPresent()){
            for (int y = h; y < h + 2; y++) {
                collision[width - 1][y] = false;
            }
            if(right.get().isExit()) {
                var pos = new Vertex(
                        position.x * width * tileset.tileWidth * tilemap.scale,
                        position.y * height * tileset.tileHeight * tilemap.scale);
                pos.x += (width - 1) * tileset.tileHeight * tilemap.scale;
                pos.y += h * tileset.tileHeight * tilemap.scale;
                doors.add(new Door(pos,
                        tileset.tileWidth * tilemap.scale,
                        tileset.tileHeight * tilemap.scale * 2,
                        tileset.GetTile("0;0").image,
                        right.get().child()));
            }
        }
        var left = openDirections.stream().filter((
                c -> c.direction().equals(new VertexInt(-1,0))))
                .findFirst();
        if (left.isPresent()){
            for (int y = h; y < h + 2; y++) {
                collision[0][y] = false;
            }
            if (left.get().isExit()){
                var pos = new Vertex(
                        position.x * width * tileset.tileWidth * tilemap.scale,
                        position.y * height * tileset.tileHeight * tilemap.scale);
                pos.y += h * tileset.tileHeight * tilemap.scale;
                doors.add(new Door( pos,
                        tileset.tileWidth * tilemap.scale,
                        tileset.tileHeight * tilemap.scale * 2,
                        tileset.GetTile("0;0").image,
                        left.get().child()));
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

        for(GameObj wall : walls){
            getInstance().Instantiate(L_STATICS,wall);
        }
        for (var door : doors){
            getInstance().Instantiate(L_STATICS,door);
        }
        getInstance().Instantiate(L_ENVIRONMENT,environment);
    }
}

package net.casqan.scifigame.dungeon;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.Rect;
import net.casqan.scifigame.extensions.VertexInt;
import net.casqan.scifigame.gizmos.Gizmo;
import net.casqan.scifigame.gizmos.Gizmos;
import net.casqan.scifigame.tilesystem.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Dungeon extends Graph<Room> {
    static Random random;
    public Dungeon(Room rootObj) {
        super(rootObj);
    }

    public static Dungeon Generate(long seed, int maxBranches, int minBranches, int maxBranchDepth, int minBranchDepth,
                                   int roomWidth, int roomHeight, int roomSpacing, Tileset tileset){
        Dungeon dungeon = new Dungeon(new Room(new VertexInt(0,0)));
        dungeon.Root().data.position = new VertexInt(0,0);
        random = new Random();
        random.setSeed(seed);
        int branchCount = maxBranches != minBranches ?
                random.nextInt(maxBranches - minBranches) + minBranches
                : maxBranches;
        for (int i = 0; i < branchCount; i++) {
            Node<Room> current;
            if (i == 0) current = dungeon.Root();
            else current = dungeon.nodes.get(random.nextInt(dungeon.nodes.size()));
            Node<Room> roomNode = current;
            int count = 0;
            while (roomNode.parent != null){
                roomNode = roomNode.parent;
                count++;
            }
            int branchDepth = maxBranchDepth > minBranchDepth + count ?
                    random.nextInt(maxBranchDepth - minBranchDepth) + minBranchDepth
                    : maxBranchDepth;
            branchDepth -= count;
            for (int j = 0; j < branchDepth; j++) {
                Room room = BuildRoom(dungeon,current,0);
                if (current == null) {
                    System.out.println("Current room is, null! Something went wrong...");
                    break;
                }
                if (room != null){
                    Node<Room> next = new Node<>(room, current);
                    dungeon.Add(current, next);
                    current = next;
                }else {
                    j--;
                    current = current.parent;
                }
            }
        }
        dungeon.Root().data.type = RoomType.Start;
        return dungeon;
    }
    public static Room BuildRoom(Dungeon dungeon, Node<Room> node, int searched){
        if (searched == 15) return null;
        var dir = (int)Math.pow(2,random.nextInt(4)) ;
        if ((searched & 1) == dir){
            return BuildRoom(dungeon, node, searched);
        } else if ((searched & 2) == dir){
            return BuildRoom(dungeon, node, searched);
        }else if ((searched & 4) == dir){
            return BuildRoom(dungeon, node, searched);
        } else if ((searched & 8) == dir){
            return BuildRoom(dungeon, node, searched);
        }
        System.out.println("Dir " + dir);
        System.out.println("Searched " + searched);
        boolean occupied = true;
        try{
            occupied = dungeon.nodes.stream().anyMatch(
                    (roomNode -> Objects.equals(roomNode.data.position,
                            VertexInt.Add(node.data.position, GetDirection(dir)))));
        } catch (Exception e){
            System.out.println(e);
            return null;
        }

        System.out.println(occupied);
        if(occupied){
            return BuildRoom(dungeon, node, searched + dir);
        }
        return new Room(VertexInt.Add(node.data.position,GetDirection(dir)));
    }

    public static VertexInt GetDirection(int dir){
        switch (dir){
            case 1:
                return new VertexInt(1,0);
            case 2:
                return new VertexInt(0,1);
            case 4:
                return new VertexInt(-1,0);
            case 8:
                return new VertexInt(0,-1);
            default:
                return new VertexInt(0,0);
        }
    }
    public static int GetDirection(VertexInt dir) {
        if (dir.x == 1 && dir.y == 0) return 1;
        if (dir.x == 0 && dir.y == 1) return 2;
        if (dir.x == -1 && dir.y == 0) return 4;
        if (dir.x == 0 && dir.y == -1) return 8;
        return 0;
    }

    public static void CreateDungeonGizmos(Dungeon dungeon,int roomSize){
        for(int i = 0; i < dungeon.nodes.size(); i++){
            var room = dungeon.nodes.get(i);

            var r = new Rect(room.data.position.mult(roomSize),new Vertex(1,1).mult(roomSize));

            Gizmos.Add(new Gizmo(r,new Color((255 / dungeon.nodes.size()) * i,
                    255 - ((255 / dungeon.nodes.size()) * i),0),true));
            if(room.parent == null) continue;
            Vertex dir = Vertex.sub(room.parent.data.position.mult(roomSize),
                    room.data.position.mult(roomSize));
            Rect c;
            if (dir.y < 0 || dir.x < 0){
                dir.x = Math.abs(dir.x);
                dir.y = Math.abs(dir.y);
                c = new Rect(
                        Vertex.add(room.parent.data.position.mult(roomSize),
                                new Vertex(roomSize / 2,roomSize / 2)),
                        dir);
            }else {
                c = new Rect(
                        Vertex.add(room.data.position.mult(roomSize),
                                new Vertex(roomSize / 2,roomSize / 2)),
                        dir);
            }
            Gizmos.Add(new Gizmo(c, Color.blue));
        }
    }

    public static List<Corridor> GetCorridors(Node<Room> node) {
        List<Corridor> dirs = new ArrayList<>();
        if (node.parent != null)
            dirs.add(new Corridor(GetOpenDirections(node, node.parent),false,node.parent,node));
        for (Node<Room> child : node.children) {
            dirs.add(new Corridor(GetOpenDirections(node,child),true,node,child));
        }
        return dirs;
    }
    public static VertexInt GetOpenDirections(Node<Room> node, Node<Room> other) {
        VertexInt dir = VertexInt.Sub(other.data.position, node.data.position);
        if (dir.y != 0 )dir.y /= Math.abs(dir.y);
        if (dir.x != 0 )dir.x /= Math.abs(dir.x);
        return dir;
    }
}

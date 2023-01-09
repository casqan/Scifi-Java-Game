package net.casqan.scifigame.generation;

import net.casqan.scifigame.extensions.VertexInt;

import java.util.Objects;
import java.util.Random;

public class Dungeon extends Graph<Room> {
    static Random random;
    public Dungeon(Room rootObj) {
        super(rootObj);
    }

    //Dungeon generation Algorythm
    //Start at a root room
    //Step forward in any direction
    //If the room is not occupied, place it
    //If the room is occupied, step back and try again
    //Do this until Max room depth Has been reached
    //Take a random room and step forward in any direction
    //If the room is not occupied, place it
    //Do this for a random amount of branches.
    public static Dungeon Generate(long seed, int maxBranches, int minBranches, int maxBranchDepth, int minBranchDepth){
        Dungeon dungeon = new Dungeon(new Room());
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
        var room = new Room();
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
        room.position = VertexInt.Add(node.data.position,GetDirection(dir));
        return room;
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
}

package net.casqan.scifigame.generation;

import java.util.ArrayList;
import java.util.List;

public class Node<T>{
    public Node<T> parent;
    public List<Node<T>> children = new ArrayList<>();
    public T data;
    public Node(T data){
        this.data = data;
    }
    public Node(T data,Node<T> parent){
        this(data);
        this.parent = parent;
        this.parent.children.add(this);
    }
}

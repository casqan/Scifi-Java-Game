package net.casqan.scifigame.generation;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {
    public List<Node<T>> nodes;
    public Node<T> Root(){
        return nodes.get(0);
    }
    public Graph(T rootObj){
        nodes = new ArrayList<>();
        nodes.add(new Node<T>(rootObj));
    }
    public void Insert(Node<T> child, Node<T> node){
        Insert(child.parent,child,node);
    }
    public void Insert(Node<T> parent,Node<T> child, Node<T> node){
        child.parent = node;
        parent.children.add(node);
    }
    public void Add(Node<T> parent,Node<T> node){
        parent.children.add(node);
        nodes.add(node);
    }
}

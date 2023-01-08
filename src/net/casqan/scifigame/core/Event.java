package net.casqan.scifigame.core;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class Event<T> {
    ArrayList<Consumer<T>> listeners = new ArrayList<>();

    public void AddListener(Consumer<T> listener){
        listeners.add(listener);
    }
    public void RemoveListener(Consumer<T> listener){
        listeners.remove(listener);
    }
    public void Clear(){
        listeners.clear();
    }
    public void Invoke(T t){
        for (var listener:
             listeners) {
            try {
                listener.accept(t);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }
}

package net.casqan.scifigame.input;

import net.casqan.scifigame.core.Event;

import java.util.HashMap;
import java.util.function.Consumer;

public final class InputManager {

    public static HashMap<Integer,KeyAction> keys = new HashMap<>();

    public static void RegisterOnKeyDown(int key, Consumer<Integer> listener){
        if (!keys.containsKey(key)) keys.put(key, new KeyAction(key));
        keys.get(key).getOnKeyDown().AddListener(listener);
    }
    public static void RegisterOnKeyUp(int key, Consumer<Integer> listener){
        if (!keys.containsKey(key)) keys.put(key, new KeyAction(key));
        keys.get(key).getOnKeyUp().AddListener(listener);
    }

    public static void SetPressed(int key,boolean pressed){
        if (!keys.containsKey(key)) return;
        keys.get(key).setPressed(pressed);
    }
}

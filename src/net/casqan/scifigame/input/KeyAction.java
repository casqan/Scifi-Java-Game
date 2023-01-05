package net.casqan.scifigame.input;

import jdk.jfr.Event;

import java.util.function.Function;

public class KeyAction {
    int key;
    boolean isPressed = false;

    KeyInteractionEvent onKeyDown;
    KeyInteractionEvent onKeyUp;

    public KeyAction(int keyID){
        key = keyID;
        onKeyDown = new KeyInteractionEvent();
        onKeyUp = new KeyInteractionEvent();
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        if (pressed && !isPressed) onKeyDown.Invoke(key);
        else if (!pressed && isPressed) onKeyUp.Invoke(key);
        isPressed = pressed;
    }

    public KeyInteractionEvent getOnKeyDown() {
        return onKeyDown;
    }

    public KeyInteractionEvent getOnKeyUp() {
        return onKeyUp;
    }
}

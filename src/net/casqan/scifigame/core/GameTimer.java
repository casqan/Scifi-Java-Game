package net.casqan.scifigame.core;

import java.util.function.Consumer;

public class GameTimer {
    Runnable action;
    double startTime;
    double interval;
    boolean repeat;

    public GameTimer(Runnable action, double interval, boolean repeat){
        this.action = action;
        this.interval = interval;
        this.repeat = repeat;
        GameTime.RegisterTimer(this);
    }
    public void Update(){
        if (GameTime.Time() > startTime + interval){
            action.run();
            if (repeat) startTime = GameTime.Time() + interval;
            GameTime.UnRegisterTimer(this);
        }
    }
}

package net.casqan.scifigame.core;

public final class GameTime {

    static long initTime;
    static double deltaTime;
    static double time;

    public static void Init(){
        initTime = System.currentTimeMillis();
    }

    public static double DeltaTime(){
        return deltaTime;
    }
    public static void Update(){
        var current = (System.currentTimeMillis() - initTime) * .001d;
        deltaTime = current - time;
        time = current;
    }

    public static double Time(){
        return time;
    }

}

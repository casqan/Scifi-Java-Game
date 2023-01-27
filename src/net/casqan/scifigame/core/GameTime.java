package net.casqan.scifigame.core;

import java.util.List;

public final class GameTime {

    public static List<GameTimer> gameTimers;
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
        /*for (var gt:
             gameTimers) {
            gt.Update();
        }*/
        deltaTime = current - time;
        time = current;
    }

    public static double Time(){
        return time;
    }
    public static void RegisterTimer(GameTimer gameTimer){
        gameTimers.add(gameTimer);
    }

    public static boolean UnRegisterTimer(GameTimer GameTimer) {
        return gameTimers.remove(GameTimer);
    }
}

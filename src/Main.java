import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.Launcher;

import java.util.Arrays;

public class Main{
    public static void main(String[] args) {
        int width = 1280;
        int height = 720;
        int seed = Integer.MIN_VALUE;
        if (Arrays.asList(args).contains("-width")
                || Arrays.asList(args).contains("-w")) {
            width = Integer.parseInt(args[Arrays.asList(args).indexOf("-width") + 1]);
        }
        if (Arrays.asList(args).contains("-height")
                || Arrays.asList(args).contains("-h")) {
            height = Integer.parseInt(args[Arrays.asList(args).indexOf("-height") + 1]);
        }
        if (Arrays.asList(args).contains("-seed")
                || Arrays.asList(args).contains("-s")) {
            seed = Integer.parseInt(args[Arrays.asList(args).indexOf("-seed") + 1]);
        }
        if (Arrays.asList(args).contains("-debug")
                || Arrays.asList(args).contains("-d")) {
            Game2D.debug = true;
        }
        Game2D game = new Game2D(width,height,seed);
        game.play();
    }
}

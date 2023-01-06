package net.casqan.scifigame.sprite;

import net.casqan.scifigame.core.Event;
import net.casqan.scifigame.core.GameTime;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Animation {
    SpriteSheet sheet;
    public int frameCount;
    boolean looping;
    int framerate;
    double time;
    int index;

    public Event<Animation> onAnimationEnd = new Event<>();

    public  Animation(){
    }
    public Animation Play(){
        time = GameTime.Time();
        index = 0;
        return this;
    }

    Image GetFrame(int frame){
        return sheet.GetFrame(frame);
    }

    //Integer math would be more efficient than this, but I'm lazy.
    //It's easier to work with a double that gives seconds and fractions of seconds
    //You could also offload this into a NonLoopingAnimation and then do this to
    //save on the if check, but again, I'm lazy
    Image GetCurrentFrame(){
        int frameIndex = (int)((GameTime.Time() - time) * framerate);
        if (!looping && frameIndex >= frameCount) onAnimationEnd.Invoke(this);
        return GetFrame(frameIndex % frameCount);
    }

    public Animation(SpriteSheet sheet, int framerate, boolean looping) {
        this.sheet = sheet;
        this.framerate = framerate;
        this.looping = looping;
        this.frameCount = sheet.spriteCount;
    }

    public SpriteSheet getSheet() {
        return sheet;
    }

    public int getFramerate() {
        return framerate;
    }

    public void setFramerate(int framerate) {
        this.framerate = framerate;
    }
}

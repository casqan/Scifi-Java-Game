package net.casqan.scifigame.sprite;

import java.awt.*;

public class Animation {
    SpriteSheet sheet;
    public int frameCount;
    boolean looping;
    int framerate;
    public  Animation(){
    }

    Image GetFrame(int frame){
        return sheet.GetFrame(frame);
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

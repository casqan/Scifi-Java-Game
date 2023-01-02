package net.casqan.scifigame.sprite;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;
import net.casqan.scifigame.core.*;

import java.awt.*;
import java.util.HashMap;

public class Entity extends AbstractGameObj {

    Vertex pos;
    Vertex velocity;
    int height;
    int width;

    Image image;
    HashMap<EntityAction,Animation> animations;
    EntityAction currentAction = EntityAction.idle;

    int framerate;
    int frameIndex;
    final static Color shadow = new Color(0,0,0,0.1f);

    public Entity(HashMap<EntityAction,Animation> animations, Vertex pos, Vertex velocity,EntityAction currentAction){
        super(pos,velocity,animations.get(currentAction).sheet.scaled.x,animations.get(currentAction).sheet.scaled.y);

        this.animations = animations;
        this.currentAction = currentAction;
        frameIndex = 0;
        image = animations.get(currentAction).GetFrame(frameIndex);
        this.pos = pos;
        this.velocity = velocity;
        this.height = animations.get(currentAction).sheet.scaled.y;
        this.width = animations.get(currentAction).sheet.scaled.x;
    }

    @Override
    public Vertex pos() {
        return pos;
    }

    @Override
    public Vertex velocity() {
        return velocity;
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

    Animation CurrentAnim(){
        return animations.get(currentAction);
    }

    Image GetFrame(){
        var index = (int) (GameTime.Time() * CurrentAnim().framerate) % CurrentAnim().frameCount;
        return CurrentAnim().GetFrame(index);
    }

    @Override
    public void paintTo(Graphics g) {
        g.drawImage(GetFrame(),(int)pos.x,(int)pos.y,null);
        g.setColor(shadow);
        g.fillOval((int)pos.x + width / 2,(int)pos.y + height,64,32);
    }
}

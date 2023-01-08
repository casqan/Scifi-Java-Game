package net.casqan.scifigame.entities;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.animations.Animation;
import net.casqan.scifigame.animations.EntityAction;

import java.util.HashMap;

public class Character extends Entity{
    public boolean attacking = false;

    public Character(HashMap<String, Animation> animations, Vertex pos, Vertex anchor, int width, int height, Vertex velocity, String currentAction) {
        super(animations, pos, anchor, width, height, velocity, currentAction);
    }

    //This is probably not the cleanest way to do this, it would be better to wrap this in a function,
    //That Takes the direction and all Directional Animations, but IDC RN
    public void Attack(){
        attacking = true;
        if (forward().x > 0 && (!currentAction.equals(EntityAction.ATTACKPX))) SetCurrentAction(EntityAction.ATTACKPX);
        if (forward().x < 0 && (!currentAction.equals(EntityAction.ATTACKNX))) SetCurrentAction(EntityAction.ATTACKNX);
        if (forward().y > 0 && (!currentAction.equals(EntityAction.ATTACKPY))) SetCurrentAction(EntityAction.ATTACKPY);
        if (forward().y < 0 && (!currentAction.equals(EntityAction.ATTACKNY))) SetCurrentAction(EntityAction.ATTACKNY);
    }

    @Override
    public void move(){
        if (attacking) return;
        super.move();
        if (velocity.magnitude() > 0){
            forward.x = velocity.x;
            forward.y = velocity.y;
            if (velocity().y > 0)       { if (!currentAction.equals(EntityAction.MOVEPY)) SetCurrentAction(EntityAction.MOVEPY); }
            else if (velocity().y < 0)  { if (!currentAction.equals(EntityAction.MOVENY)) SetCurrentAction(EntityAction.MOVENY); }
            else if (velocity().x > 0)  { if (!currentAction.equals(EntityAction.MOVEPX)) SetCurrentAction(EntityAction.MOVEPX); }
            else if (velocity().x < 0)  { if (!currentAction.equals(EntityAction.MOVENX)) SetCurrentAction(EntityAction.MOVENX); }
        }else{
            if (forward().y > 0)        { if (!currentAction.equals(EntityAction.IDLEPY)) SetCurrentAction(EntityAction.IDLEPY); }
            else if (forward().y < 0)   { if (!currentAction.equals(EntityAction.IDLENY)) SetCurrentAction(EntityAction.IDLENY); }
            else if (forward().x > 0)   { if (!currentAction.equals(EntityAction.IDLEPX)) SetCurrentAction(EntityAction.IDLEPX); }
            else if (forward().x < 0)   { if (!currentAction.equals(EntityAction.IDLENX)) SetCurrentAction(EntityAction.IDLENX); }
        }
    }
}

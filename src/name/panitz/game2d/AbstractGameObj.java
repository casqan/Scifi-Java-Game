package name.panitz.game2d;

import net.casqan.scifigame.Game2D;
import net.casqan.scifigame.core.Camera;

import java.awt.*;

public abstract class AbstractGameObj implements GameObj{
  public Vertex pos;
  public String name;
  public Vertex velocity;
  public Vertex anchor;
  public double width;
  public double height;

  public Vertex pos(){return pos;}
  public Vertex velocity(){return velocity;}
  public String name(){return name;}
  public Vertex getScreenPos(){
      return Vertex.add(Vertex.sub(pos(),Camera.Main().pos()),Game2D.getInstance().getScreen());
  }
  /**
   * Added an Anchor point to more accurately Model the Hitbox of an Object, whose sprite fills more than
   * the given game Objects size.
   */
  public Vertex anchor() {return anchor;}

  public double width(){return width;}
  public double height(){return height;}

  public AbstractGameObj(){
    pos = new Vertex(0,0);
    velocity = new Vertex(0,0);
    width = 0;
    height = 0;
  }
  public AbstractGameObj(Vertex p, Vertex v, double w, double h){
    pos=p; velocity=v; width=w; height=h;
  }
}


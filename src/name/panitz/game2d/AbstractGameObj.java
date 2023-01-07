package name.panitz.game2d;
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
  /**
   * Added an Anchor point to more accurately Model the Hitbox of an Object, whose sprite fills more than
   * the given game Objects size.
   */
  public Vertex anchor() {return anchor;}

  public double width(){return width;}
  public double height(){return height;}

  public AbstractGameObj(){}
  public AbstractGameObj(Vertex p, Vertex v, double w, double h){
    pos=p; velocity=v; width=w; height=h;
  }
}


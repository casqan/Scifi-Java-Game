package name.panitz.game2d;
public interface GameObj{
  Vertex pos();
  Vertex velocity();
  Vertex anchor();
  double width();
  double height();
  String name();

  default int getZIndex(){
    return (int)((pos().y + anchor().y) * 100);
  };

  void paintTo(java.awt.Graphics g);

  default void move(){pos().add(velocity());}

  default boolean isAbove(double y){return pos().y + height() + anchor().y<y;}
  default boolean isAbove(GameObj that){return isAbove(that.pos().y + that.anchor().y);}

  default boolean isUnderneath(GameObj that){return that.isAbove(this);}

  default boolean isLeftOf(double x){return pos().x + anchor().x + width()<x;}
  default boolean isLeftOf(GameObj that){return isLeftOf(that.pos().x + that.anchor().x);}
  default boolean isRightOf(GameObj that){return that.isLeftOf(this);}

  default boolean touches(GameObj that){
    return 
     ! (    isAbove(that)  || isUnderneath(that)
         || isLeftOf(that) || isRightOf(that)    );
  }

  default boolean isStandingOnTopOf(GameObj that) {
	    return 
	    		 !(isLeftOf(that) || isRightOf(that)) 
	    		&& isAbove(that)
	            && pos().y + height() + velocity().y+1.5 > that.pos().y;
	  }

}


package name.panitz.game2d;
import java.awt.*;
public record TextObject(String name, Vertex pos, Vertex velocity,Vertex anchor
                 , double width, double height
                 , int fontSize, String fontName, String text)
     implements GameObj{

  public TextObject( Vertex pos, String text){
    this("", pos,new Vertex(0,0),new Vertex(0,0),0,0,20,"Helvetica",text);
  }


  @Override
  public void Update() {

  }

  public void paintTo(Graphics g){
    g.setFont(new Font(fontName, Font.PLAIN, fontSize));
    g.drawString(text, (int)pos().x, (int)pos().y);
  }
}


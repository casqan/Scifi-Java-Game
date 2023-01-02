package name.panitz.game2d.simple;
import java.util.List;

import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.ImageObject;
import name.panitz.game2d.TextObject;
import name.panitz.game2d.Vertex;

import java.util.ArrayList;
import java.awt.event.*;
import static java.awt.event.KeyEvent.*;

record SimpleGame
  ( GameObj player, List<List<? extends GameObj>> goss
  , int width, int height, int[] schaden
  , List<GameObj> hintergrund, List<GameObj> gegner
  , List<GameObj> wolken, List<GameObj> texte) 
    implements Game{


  SimpleGame(){
    this
     ( new ImageObject(new Vertex(200,200),new Vertex(1,1),"hexe.png")
     , new ArrayList<>(), 800, 600, new int[]{0}
     , new ArrayList<>(), new ArrayList<>()
     , new ArrayList<>(), new ArrayList<>());
  }


  public void init(){
    goss().clear();
    goss().add(hintergrund());
    goss().add(gegner());
    goss().add(wolken());
    goss().add(texte());
    hintergrund().clear();
    gegner().clear();
    wolken().clear();
    texte().clear();


    hintergrund().add(new ImageObject("wiese.jpg"));

    
    wolken().add(new ImageObject(
                  new Vertex(800,10),new Vertex(-1,0),"wolke.png"));
    wolken().add(new ImageObject(
                  new Vertex(880,90),new Vertex(-1.2,0),"wolke.png"));
    wolken().add(new ImageObject(
                  new Vertex(1080,60),new Vertex(-1.1,0),"wolke.png"));
    wolken().add(new ImageObject(
                  new Vertex(980,110),new Vertex(-0.9,0),"wolke.png"));


    gegner().add(new ImageObject(
                  new Vertex(800,100),new Vertex(-1,0),"biene.png"));
    gegner().add(new ImageObject(
                  new Vertex(800,300),new Vertex(-1.5,0),"biene.png"));

    texte().add(new TextObject(new Vertex(10,30)
                              ,"Bienenstiche: 0"));
  }

  public void doChecks(){

    for (var w:wolken()) if (w.isLeftOf(0)) {w.pos().x = width();}


    for (var g:gegner()){
      if (g.isLeftOf(0))     {g.pos().x = width();}


      if (player.touches(g)) {
        g.pos().moveTo(new Vertex(width()+10,g.pos().y));
        schaden[0]++;
        texte().clear();
        texte().add(new TextObject(new Vertex(10,30)
                                  ,"Bienenstiche: "+schaden[0]));
      }
    }
    }

  public void keyPressedReaction(KeyEvent keyEvent){
    switch (keyEvent.getKeyCode()){
      case VK_RIGHT -> player().velocity().add(new Vertex(1,0));
      case VK_LEFT  -> player().velocity().add(new Vertex(-1,0));
      case VK_DOWN  -> player().velocity().add(new Vertex(0,1));
      case VK_UP    -> player().velocity().add(new Vertex(0,-1));
    }
  }

	@Override
	public boolean won() {
		return false;
	}

	@Override
	public boolean lost() {
		return false;
	}


  public static void main(String... args){
    new SimpleGame().play();
  }
}


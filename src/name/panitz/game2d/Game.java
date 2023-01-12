package name.panitz.game2d;
import net.casqan.scifigame.input.InputManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

import static java.awt.event.KeyEvent.VK_F11;

public interface Game{


  int width();
  int height();
  void width(int width);
  void height(int height);

  GameObj player();

  HashMap<String, List<GameObj>> goss();

  void init();


  void doChecks();

  void keyPressedReaction(KeyEvent keyEvent);
  void keyReleasedReaction(KeyEvent keyEvent);
  void keyTypedReaction(KeyEvent keyEvent);

  default void move(){
    if (ended()) return;
    for (var key:goss().keySet()) for (var go : goss().get(key)) go.move();
    player().move();
  }

  boolean won();
  boolean lost();
  void destroy(GameObj go, String layer);

  default boolean ended() {
    return won()||lost();
  }


  default void paintTo(Graphics g){
    for (var key:goss().keySet()) for (var go : goss().get(key)) go.paintTo(g);
    player().paintTo(g);
  }


  default void play(){
    init();
    var f = new javax.swing.JFrame();
    InputManager.RegisterOnKeyDown(VK_F11,(var) ->{
      if(f.isUndecorated()){
        f.dispose();
        f.setUndecorated(false);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        f.pack();
        f.setVisible(true);
      }else {
        f.dispose();
        f.setUndecorated(true);
        f.setExtendedState(Frame.NORMAL);
        f.pack();
        f.setVisible(true);
      }
    });
    f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    f.add(new SwingScreen(this));
    f.pack();
    f.setVisible(true);
  }
}


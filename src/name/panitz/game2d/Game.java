package name.panitz.game2d;
import net.casqan.scifigame.input.InputManager;

import javax.sound.midi.Soundbank;
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
  void Destroy(GameObj go, String layer);

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
    f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    var ss = new SwingScreen(this);
    InputManager.RegisterOnKeyDown(VK_F11,(var) ->{
      Dimension dim = null;
      if(f.isUndecorated()){
        f.dispose();
        f.setUndecorated(false);
        f.setExtendedState(Frame.NORMAL);
        dim = new Dimension(1280,720);
      }else {
        f.dispose();
        f.setUndecorated(true);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        dim = f.getToolkit().getScreenSize().getSize();
        System.out.println(dim.width);
        System.out.println(dim.height);
      }
      f.setSize(dim);
      ss.setSize(dim);
      f.pack();
      f.setVisible(true);
    });
    f.add(ss);
    f.pack();
    f.setVisible(true);
  }
}


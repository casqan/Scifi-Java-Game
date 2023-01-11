package name.panitz.game2d;
import net.casqan.scifigame.input.InputManager;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import static java.awt.event.KeyEvent.VK_F11;

public class SwingScreen extends JPanel implements ComponentListener{
  private static final long serialVersionUID = 1403492898373497054L;
  Game logic;
  Timer t;

  public SwingScreen(Game gl) {
    this.logic = gl;
    this.addComponentListener(this);

    t = new Timer(13, (ev)->{
        logic.move();
        logic.doChecks();
        repaint();
        getToolkit().sync();
        requestFocus();
      });
      t.start();

		
    addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            logic.keyReleasedReaction(e);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            logic.keyTypedReaction(e);
        }

        @Override public void keyPressed(KeyEvent e) {
          logic.keyPressedReaction(e);
        }
      });
      setFocusable(true);
      requestFocus();
    }

	
  @Override public Dimension getPreferredSize() {
    return new Dimension((int)logic.width(),(int)logic.height());
  }

	
  @Override protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    logic.paintTo(g);
  }

    @Override
    public void componentResized(ComponentEvent e) {
        logic.width(e.getComponent().getWidth());
        logic.height(e.getComponent().getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}


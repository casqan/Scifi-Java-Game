package net.casqan.scifigame;

import javax.swing.*;

public class Launcher extends JFrame {

    int width;
    int height;

    public void Open(){
        var g = getGraphics();
        JTextField textField = new JTextField();
        textField.addActionListener((event) -> {
            switch (event.getActionCommand()) {
                case "":
                    
                    break;
                default:
                    break;
            }
        });
    }
}

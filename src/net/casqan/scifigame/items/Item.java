package net.casqan.scifigame.items;

import net.casqan.scifigame.entities.Statistics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.Transient;

public class Item {
    private Image icon;
    public String iconPath;
    public String name;
    public String description;
    public int price;
    public Statistics statistics;

    public Image getIcon(){
        return icon;
    }

    public void EnsureLoad(){
        if (icon != null) return;
        try{
            icon = ImageIO.read(getClass().getClassLoader().getResource("resources/" + iconPath));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public Item(String iconPath, String name, String description, int price, Statistics statistics) {
        this.iconPath = iconPath;
        this.name = name;
        this.description = description;
        this.price = price;
        this.statistics = statistics;
    }

    public Item(String iconPath, String name, String description, int price) {
        this.iconPath = iconPath;
        this.name = name;
        this.description = description;
        this.price = price;
        this.statistics = new Statistics();
    }
}

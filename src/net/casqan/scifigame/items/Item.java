package net.casqan.scifigame.items;

import net.casqan.scifigame.entities.Statistics;

import java.awt.*;

public class Item {
    public Image icon;
    public String name;
    public String description;
    public int price;
    public Statistics statistics;

    public Item(Image icon, String name, String description, int price, Statistics statistics) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.price = price;
        this.statistics = statistics;
    }

    public Item(Image icon, String name, String description, int price) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.price = price;
        this.statistics = new Statistics();
    }
}

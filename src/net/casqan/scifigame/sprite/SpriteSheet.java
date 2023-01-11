package net.casqan.scifigame.sprite;

import name.panitz.game2d.Vertex;
import net.casqan.scifigame.extensions.VertexInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    VertexInt dimensions;
    VertexInt scaled;

    int spriteCount;
    float scale;
    Image sheet;
    Image[] sprites;

    public SpriteSheet(){

    }

    public int getSpriteWidth() {
        return dimensions.x;
    }

    public int getSpriteHeight() {
        return dimensions.y;
    }

    public int getSpriteCount() {
        return spriteCount;
    }
    public VertexInt getScaled(){return scaled;}

    public Image getSheet() {
        return sheet;
    }


    public SpriteSheet(String fileName, VertexInt dimensions, float scale){
        sheet = null;
        try{
            this.dimensions = dimensions;

            sheet = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName));
            spriteCount = sheet.getHeight(null) / dimensions.y;
            sprites = new Image[spriteCount];
            System.out.println(spriteCount);
            scaled = dimensions.multInt(scale);

            for (int i = 0;i < spriteCount; i++){
                sprites[i] = BuildSprite(i);
            }

        } catch (Exception e){
            System.out.println("Could not load: " + fileName);
            System.out.println(e);
        }
    }

    //https://stackoverflow.com/questions/2386064/how-do-i-crop-an-image-in-java
    //https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html#:~:text=A%20BufferedImage%20is%20comprised%20of,of%20(0%2C%200).
    //https://stackoverflow.com/questions/4216123/how-to-scale-a-bufferedimage
    public Image BuildSprite(int index){
        var dest = new BufferedImage(
                scaled.x,
                scaled.y,
                BufferedImage.TYPE_INT_ARGB);

        Graphics g = dest.getGraphics();
        g.drawImage(sheet,
                0, 0, scaled.x, scaled.y,
                0, dimensions.y * index, dimensions.x, dimensions.y * (index + 1), null);
        g.dispose();
        dest.flush();
        return dest;
    }

    public Image GetFrame(int index){
        return sprites[index];
    }
}

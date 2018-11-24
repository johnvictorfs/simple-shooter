package com.jetbrains;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class Entity {
    private int x_coord;
    private int y_coord;
    private int width;
    private int height;
    private BufferedImage sprite;

    void setSprite(String name) {
        String baseDir = new File("").getAbsolutePath();
        String spritePath = baseDir + "/src/assets/" + name + ".png".replace("/", File.separator);
        try {
            sprite = ImageIO.read(new File(spritePath));
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
        } catch (java.io.IOException e) {
            sprite = null;
        }
    }

    BufferedImage getSprite() {
        return this.sprite;
    }

    Rectangle getRectangle() {
        return new Rectangle(this.x_coord, this.y_coord, this.width-6, this.height-6);
    }

    void setX(int x) {
        this.x_coord = x;
    }

    void setY(int y) {
        this.y_coord = y;
    }

    int getX() {
        return this.x_coord;
    }

    int getY() {
        return this.y_coord;
    }

    void moveX(int x) {
        this.x_coord += x;
    }

    void moveY(int y) {
        this.y_coord += y;
    }
}

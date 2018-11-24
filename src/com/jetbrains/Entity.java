package com.jetbrains;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

class Entity {
    private int x_coord;
    private int y_coord;
    private BufferedImage sprite;

    public void setSprite(String name) {
        String baseDir = new File("").getAbsolutePath();
        String spritePath = baseDir + "/src/assets/" + name + ".png".replace("/", File.separator);
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (java.io.IOException e) {
            sprite = null;
        }
    }

    public BufferedImage getSprite() {
        return this.sprite;
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

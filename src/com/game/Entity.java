package com.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Entity {
    private int x_coord;
    private int y_coord;
    private int width;
    private int height;
    private BufferedImage sprite;

    void setSprite(String name) {
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/" + name + ".png"));
        } catch (IOException e) {
            sprite = null;
        }
        if (sprite != null) {
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
        }
        else {
            this.width = 32;
            this.height = 32;
        }
    }

    BufferedImage getSprite() { return this.sprite; }

    Rectangle getRectangle() {
        if (this.sprite != null) {
            return new Rectangle(this.x_coord, this.y_coord, this.width - 10, this.height - 10);
        }
        return new Rectangle(this.x_coord, this.y_coord, 32, 32);
    }

    void setX(int x) { this.x_coord = x; }

    void setY(int y) { this.y_coord = y; }

    int getX() { return this.x_coord; }

    int getY() { return this.y_coord; }

    void moveX(int x) { this.x_coord += x; }

    void moveY(int y) { this.y_coord += y; }
}

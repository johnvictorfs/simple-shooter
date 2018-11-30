package com.game;

import com.exceptions.EntityOutOfBoundsException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

class Entity {
    private int x_coord;
    private int y_coord;
    private int width;
    private int height;
    private BufferedImage sprite;

    void setSprite(String name) {

        URL path = getClass().getResource("/assets/" + name + ".png");
        if (path != null) {
            try {
                sprite = ImageIO.read(path);
                this.width = sprite.getWidth();
                this.height = sprite.getHeight();
            } catch (IOException e) {
                sprite = null;
                this.width = 32;
                this.height = 32;
            }
        } else {
            this.width = 32;
            this.height = 32;
        }
    }

    BufferedImage getSprite() {
        return this.sprite;
    }

    private Rectangle getRectangle() {
        if (this.sprite != null) {
            return new Rectangle(this.x_coord, this.y_coord, this.width - 10, this.height - 10);
        }
        return new Rectangle(this.x_coord, this.y_coord, 32, 32);
    }

    boolean intersects(Entity b) {
        return this.getRectangle().intersects(b.getRectangle());
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

    void moveX(int x) throws EntityOutOfBoundsException {
        if (this.x_coord + x > 0 && this.x_coord + x < 620) {
            this.x_coord += x;
        } else {
            throw new EntityOutOfBoundsException();
        }
    }

    void moveY(int y) throws EntityOutOfBoundsException {
        if (this.y_coord + y > -20 && this.y_coord + y < 520) {
            this.y_coord += y;
        } else {
            throw new EntityOutOfBoundsException();
        }
    }
}

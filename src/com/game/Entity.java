package com.game;

import com.exceptions.EntityOutOfBoundsException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

class Entity implements Serializable {
    private int x_coord;
    private int y_coord;
    private int width;
    private int height;
    private ImageIcon sprite;

    void setSprite(String name) {
        String strPath = "/assets/" + name + ".png".replace("/", File.separator);
        URL path = getClass().getResource(strPath);
        if (path != null) {
            try {
                sprite = new ImageIcon(ImageIO.read(path));
                //sprite = ImageIO.read(path);

                this.width = sprite.getIconWidth();
                this.height = sprite.getIconHeight();
            } catch (IOException e) {
                sprite = null;
                this.setWidth(32);
                this.setHeight(32);
            }
        } else {
            this.setWidth(32);
            this.setHeight(32);
        }
    }

    void setWidth(int width) {
        this.width = width;
    }

    void setHeight(int height) {
        this.height = height;
    }

    ImageIcon getSprite() {
        return this.sprite;
    }

    private Rectangle getRectangle() {
        if (this.sprite != null) {
            return new Rectangle(this.x_coord, this.y_coord, this.width, this.height);
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
        if (this.x_coord + x > 0 && this.x_coord + x < 665) {
            this.x_coord += x;
        } else {
            throw new EntityOutOfBoundsException();
        }
    }

    void moveY(int y) throws EntityOutOfBoundsException {
        if (this.y_coord + y > 0 && this.y_coord + y < 520) {
            this.y_coord += y;
        } else {
            throw new EntityOutOfBoundsException();
        }
    }
}

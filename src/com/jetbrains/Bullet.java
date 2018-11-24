package com.jetbrains;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet extends Entity {
    private BufferedImage sprite;
    private int speed = -10;

    Bullet(int x, int y) {
        this.setX(x);
        this.setY(y);

        String baseDir = new File("").getAbsolutePath();
        String spritePath = baseDir + "/src/assets/fireball_1.png".replace("/", File.separator);
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (java.io.IOException e) {
            System.out.println("Nao foi possivel encontrar imagem 'fireball_1.png' em " + spritePath + ". Tiros vao ser uma bola azul escura.");
            sprite = null;
        }
    }

    int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }
}

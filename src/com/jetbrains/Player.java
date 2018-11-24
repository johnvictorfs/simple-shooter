package com.jetbrains;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

class Player extends Entity {
    private BufferedImage sprite;

    Player(int x, int y) {
        this.setX(x);
        this.setY(y);
        String baseDir = new File("").getAbsolutePath();
        String spritePath = baseDir + "/src/assets/wizard_1.png".replace("/", File.separator);
        System.out.println(baseDir);
        System.out.println(spritePath);
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (java.io.IOException e) {
            System.out.println("Nao foi possivel encontrar imagem 'wizard_1.png' em /src/assets/. Jogador vai ser um retangulo azul");
            sprite = null;
        }
    }

    BufferedImage getSprite() {
        return this.sprite;
    }
}

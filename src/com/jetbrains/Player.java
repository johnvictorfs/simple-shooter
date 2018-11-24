package com.jetbrains;

import java.awt.*;

class Player extends Entity {

    private int speed;
    private int score;
    private boolean dead;

    Player(int x, int y, String name, int speed) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
        this.speed = speed;
    }

    int getSpeed() {
        return this.speed;
    }

    int getScore() {
        return this.score;
    }

    void addScore(int score) {
        this.score += score;
    }

    boolean isDead() {
        return this.dead;
    }

    void death(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 900, 900);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.red);
        g.drawString("Game Over, você morreu!", 80, 300);
        this.dead = true;
    }
}

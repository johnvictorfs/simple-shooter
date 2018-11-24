package com.jetbrains;

public class Bullet extends Entity {

    private int speed = -5;

    int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Bullet() {
    }

    Bullet(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

}

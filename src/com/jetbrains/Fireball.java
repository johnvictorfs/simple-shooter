package com.jetbrains;

class Fireball extends Entity {
    private int speed;

    Fireball(int x, int y, String name, int speed) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
        this.speed = speed;
    }

    int getSpeed() {
        return this.speed;
    }
}

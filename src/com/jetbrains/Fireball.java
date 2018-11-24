package com.jetbrains;

class Fireball extends Entity {
    private int speed = -10;

    Fireball(int x, int y, String name) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
    }

    int getSpeed() {
        return this.speed;
    }
}

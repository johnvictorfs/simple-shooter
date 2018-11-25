package com.game;

class Projectile extends Entity {
    private int speed;

    Projectile(int x, int y, String name, int speed) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
        this.speed = speed;
    }

    int getSpeed() { return this.speed; }
}

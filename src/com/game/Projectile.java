package com.game;

class Projectile extends Entity {
    private int speed;
    private int initial_x;
    private int initial_y;

    Projectile(int x, int y, String name, int speed) {
        this.setX(x);
        this.setY(y);
        this.initial_x = x;
        this.initial_y = y;
        this.setSprite(name);
        this.speed = speed;
    }

    int getSpeed() {
        return this.speed;
    }
}

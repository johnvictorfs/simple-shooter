package com.jetbrains;

class Enemy extends Entity {

    Enemy(int x, int y, String name) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
    }

}
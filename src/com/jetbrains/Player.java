package com.jetbrains;

class Player extends Entity {

    Player(int x, int y, String name) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
    }
}

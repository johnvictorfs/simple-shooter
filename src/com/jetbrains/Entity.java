package com.jetbrains;

class Entity {
    private int x_coord;
    private int y_coord;

    void setX(int x) {
        this.x_coord = x;
    }

    void setY(int y) {
        this.y_coord = y;
    }

    int getX() {
        return this.x_coord;
    }

    int getY() {
        return this.y_coord;
    }

    void moveX(int x) {
        this.x_coord += x;
    }

    void moveY(int y) {
        this.y_coord += y;
    }
}

package com.game;

import java.util.ArrayList;

class Enemy extends Entity {

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int speed;
    private int xDir;
    private int yDir;
    private boolean dead = false;

    private int initialX;
    private int initialY;

    Enemy(int x, int y, String name, int speed, int xDir, int yDir) {
        this.setX(x);
        this.setY(y);
        this.setInitialX(x);
        this.setInitialY(y);
        this.setxDir(xDir);
        this.setyDir(yDir);
        this.setSpeed(speed);
        this.setSprite(name);
    }

    void death() {
        this.dead = true;
    }

    void setAlive() {
        this.dead = false;
    }

    boolean isDead() {
        return this.dead;
    }
    ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }

    void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    int getSpeed() {
        return this.speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    int getyDir() {
        return yDir;
    }

    void setyDir(int yDir) {
        this.yDir = yDir;
    }

    int getxDir() {
        return xDir;
    }

    void setxDir(int xDir) {
        this.xDir = xDir;
    }

    int getInitialX() {
        return initialX;
    }

    void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    int getInitialY() {
        return initialY;
    }

    void setInitialY(int initialY) {
        this.initialY = initialY;
    }
}
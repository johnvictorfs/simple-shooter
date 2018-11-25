package com.game;

import java.util.ArrayList;

class Enemy extends Entity {

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int speed;
    private int xDir;
    private int yDir;

    Enemy(int x, int y, String name, int speed, int xDir, int yDir) {
        this.setX(x);
        this.setY(y);
        this.setxDir(xDir);
        this.setyDir(yDir);
        this.speed = speed;
        this.setSprite(name);
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

    public int getyDir() {
        return yDir;
    }

    public void setyDir(int yDir) {
        this.yDir = yDir;
    }

    public int getxDir() {
        return xDir;
    }

    public void setxDir(int xDir) {
        this.xDir = xDir;
    }
}
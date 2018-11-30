package com.game;

import java.util.ArrayList;

class Player extends Entity {

    private int speed;
    private int score;
    private boolean dead;
    private int initialX;
    private int initialY;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    Player(int x, int y, String name, int speed) {
        this.setX(x);
        this.setY(y);
        this.setInitialX(x);
        this.setInitialY(y);
        this.setSprite(name);
        this.speed = speed;
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

    int getScore() {
        return this.score;
    }

    void addScore() {
        this.score += 1;
    }

    void resetScore() {
        this.score = 0;
    }

    boolean isDead() {
        return this.dead;
    }

    void setAlive() {
        this.dead = false;
    }

    void death() {
        this.dead = true;
    }

    int getInitialX() {
        return initialX;
    }

    int getInitialY() {
        return initialY;
    }

    private void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    private void setInitialY(int initialY) {
        this.initialY = initialY;
    }
}

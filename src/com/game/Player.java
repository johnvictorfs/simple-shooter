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

    void addScore(int score) {
        this.score += score;
    }

    void setScore(int score) {
        this.score = score;
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

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }
}

package com.game;

import java.util.ArrayList;
import java.util.Random;

class Enemy extends Entity {

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int xDir;
    private int yDir;
    private boolean dead = false;
    private Random random = new Random();

    private int initialX;
    private int initialY;

    Enemy(int x, int y, String name, int xDir, int yDir) {
        this.setX(x);
        this.setY(y);
        this.setInitialX(x);
        this.setInitialY(y);
        this.setxDir(xDir);
        this.setyDir(yDir);
        this.setSprite(name);
    }

    void fire() {
        int randomRoll = random.nextInt(50);
        if (randomRoll > 40) {
            addProjectile(new Projectile(getX() + 20, getY() + 60, "iceball_1", random.nextInt(7) + 2));
        }
    }

    void death() { this.dead = true; }

    void setAlive() { this.dead = false; }

    boolean isDead() { return this.dead; }
    ArrayList<Projectile> getProjectiles() { return this.projectiles; }

    private void addProjectile(Projectile projectile) { projectiles.add(projectile); }

    int getyDir() { return yDir; }

    void setyDir(int yDir) { this.yDir = yDir; }

    int getxDir() { return xDir; }

    void setxDir(int xDir) { this.xDir = xDir; }

    int getInitialX() { return initialX; }

    private void setInitialX(int initialX) { this.initialX = initialX; }

    int getInitialY() { return initialY; }

    private void setInitialY(int initialY) { this.initialY = initialY; }
}
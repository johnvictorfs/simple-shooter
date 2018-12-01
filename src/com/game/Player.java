package com.game;

import java.io.*;
import java.util.ArrayList;

class Player extends Entity {

    private static final long serialVersionUID = 1113799434508676095L;
    private int speed;
    private int score;
    private int totalScore;
    private boolean dead;
    private int initialX;
    private int initialY;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    Player(int x, int y, String name, int speed, int totalScore) {
        this.setX(x);
        this.setY(y);
        this.setInitialX(x);
        this.setInitialY(y);
        this.setSprite(name);
        this.setTotalScore(totalScore);
        this.setSpeed(speed);
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
        this.totalScore += score;
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

    int getTotalScore() {
        return totalScore;
    }

    private void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private void setSpeed(int speed) {
        this.speed = speed;
    }

    void writeSave() {
        File playerSave = new File("player_save.dat");
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(playerSave));
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Jogo salvo com score total: " + this.getTotalScore());
    }

    void readSave(String path) {
        File playerSave = new File(path);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(playerSave));
            Player temp = (Player) ois.readObject();
            this.setTotalScore(temp.getTotalScore());
        } catch (IOException | ClassNotFoundException e) {
            this.writeSave();
        }
    }
}

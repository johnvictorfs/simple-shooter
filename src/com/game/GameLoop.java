package com.game;

import com.exceptions.EntityOutOfBoundsException;

import java.util.ArrayList;

public class GameLoop extends Thread {

    private Player player;
    private ArrayList<Enemy> enemies;
    private boolean running = false;

    GameLoop(Player player, ArrayList<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
    }

    void setRunning() {
        this.running = true;
    }

    void stopRunning() {
        this.running = false;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!running) {
                continue;
            }
            // Mover para cima todos os projeteis
            for (Projectile projectile : player.getProjectiles()) {
                try {
                    projectile.moveY(projectile.getSpeed());
                } catch (EntityOutOfBoundsException e) {
                    projectile.disable();
                }
            }
            for (Enemy enemy : enemies) {
                for (Projectile projectile : enemy.getProjectiles()) {
                    try {
                        projectile.moveY(projectile.getSpeed());
                    } catch (EntityOutOfBoundsException e) {
                        projectile.disable();
                    }
                }
            }

            // Adicionar 1 tiro para cada inimigo dependendo da posição das que eles já tem e da quantidade
            for (Enemy enemy : enemies) {
                // Não adicionar tiros para inimigos mortos
                if (enemy.isDead()) {
                    continue;
                }
                if (enemy.getProjectiles().size() > 0) {
                    // Caso já exista pelo menos uma bola de gelo do inimigo, apenas lançar caso a última bola de gelo
                    // lançada não esteja muito próxima dele, pra evitar spam de bolas de gelo
                    if (enemy.getProjectiles().get(enemy.getProjectiles().size() - 1).getY() - enemy.getY() > 250) {
                        // Chance de 10/50 de lancar uma bola de gelo aqui
                        enemy.fire();
                    }
                } else {
                    // Chance de 10/50 de lancar uma bola de gelo aqui
                    enemy.fire();
                }
            }

            for (Enemy enemy : enemies) {
                try {
                    enemy.moveX(enemy.getxDir());
                } catch (EntityOutOfBoundsException e) {
                    enemy.setxDir(-enemy.getxDir());
                }
                try {
                    enemy.moveY(enemy.getyDir());
                } catch (EntityOutOfBoundsException e) {
                    enemy.setyDir(-enemy.getyDir());
                }
            }
        }
    }
}

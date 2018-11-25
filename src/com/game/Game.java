package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;


public class Game extends JPanel implements KeyListener, ActionListener {
    private javax.swing.Timer timer;
    private Player player = new Player(310, 520, "wizard_1", 30);
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Projectile> playerProjectiles = new ArrayList<>();
    Random random = new Random();

    Game() {
        int delay = 8;

        enemies.add(new Enemy(100, 110, "gargoyle_1", 10, -3, -2));
        enemies.add(new Enemy(200, 140, "gargoyle_1", 20, -1, -1));
        enemies.add(new Enemy(300, 130, "gargoyle_1", 15, -1, -2));
        enemies.add(new Enemy(400, 140, "dragon_1", 5, -4, -2));
        enemies.add(new Enemy(500, 80, "dragon_1", 10, -2, -2));

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Para de desenhar na tela se o player está morto, para evitar sair da tela de Game Over
        if (player.isDead()) {
            return;
        }

        String baseDir = new File("").getAbsolutePath();
        String bgImgPath = baseDir + "/src/assets/background.png".replace("/", File.separator);
        BufferedImage bgImg;
        // Desenhar imagem de background, caso não encontre o background irá ser cinza
        try {
            bgImg = ImageIO.read(new File(bgImgPath));
            g.drawImage(bgImg, 0, 0, 700, 600, null);
        } catch (java.io.IOException e) {
            g.setColor(Color.gray);
            g.fillRect(0, 0, 700, 600);
        }

        // Desenhar o jogador, desenha a imagem dele caso encontrada, um retângulo azul claro caso não.
        if (player.getSprite() != null) {
            g.drawImage(player.getSprite(), player.getX(), player.getY(), null);
        } else {
            g.setColor(Color.cyan);
            g.fillRect(player.getX(), player.getY(), 20, 20);
        }

        // Desenhar todos os inimigos presentes
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.getSprite() != null) {
                g.drawImage(enemy.getSprite(), enemy.getX(), enemy.getY(), null);
            } else {
                g.setColor(Color.orange);
                g.drawRect(enemy.getX(), enemy.getY(), 20, 20);
            }
            if (enemy.getRectangle().intersects(player.getRectangle())) {
                player.death(g);
                return;
            }
            // Desenhar os projeteis dos inimigos
            Iterator<Projectile> eProjectileIterator = enemy.getProjectiles().iterator();
            while (eProjectileIterator.hasNext()) {
                Projectile projectile = eProjectileIterator.next();
                if (projectile.getSprite() != null) {
                    g.drawImage(projectile.getSprite(), projectile.getX(), projectile.getY(), null);
                } else {
                    g.setColor(Color.green);
                    g.drawRect(enemy.getX(), enemy.getY(), 32, 32);
                }
                // Matar o jogador com os projeteis dos inimigos
                if (projectile.getRectangle().intersects(player.getRectangle())) {
                    player.death(g);
                    return;
                }
                // Remover projeteis que sairam do mapa
                if (projectile.getY() > 700) {
                    eProjectileIterator.remove();
                }
            }
            // Matar inimigos com os projeteis do jogador
            Iterator<Projectile> pProjectileIterator = playerProjectiles.iterator();
            while (pProjectileIterator.hasNext()) {
                Projectile projectile = pProjectileIterator.next();
                if (projectile.getRectangle().intersects(enemy.getRectangle())) {
                    player.addScore(1);
                    enemyIterator.remove();
                    pProjectileIterator.remove();
                }
            }
        }

        // Desenhar todas as bolas de fogo na tela
        Iterator<Projectile> pProjectileIter = playerProjectiles.iterator();
        while (pProjectileIter.hasNext()) {
            Projectile projectile = pProjectileIter.next();
            if (projectile.getY() > 0) {
                // Desenhar a imagem da bola de fogo caso encontre imagem, ou um retângulo azul caso não.
                if (projectile.getSprite() != null) {
                    g.drawImage(projectile.getSprite(), projectile.getX(), projectile.getY(), null);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(projectile.getX(), projectile.getY(), 32, 32);
                }
            } else {
                pProjectileIter.remove();
            }
        }
        g.setColor(Color.gray);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Score: " + player.getScore(), 0, 0);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        // Mover para cima todos os projeteis
        for (Projectile projectile : playerProjectiles) {
            projectile.moveY(projectile.getSpeed());
        }
        for (Enemy enemy : enemies) {
            for (Projectile projectile : enemy.getProjectiles()) {
                projectile.moveY(projectile.getSpeed());
            }
        }

        // Adicionar 1 tiro para cada inimigo dependendo da posicao das que eles ja tem e da quantidade
        for (Enemy enemy : enemies) {
            if (enemy.getProjectiles().size() > 0) {
                if (enemy.getProjectiles().get(enemy.getProjectiles().size() - 1).getY() - enemy.getY() > 250) {
                    // Chance de 15/50 de lancar uma bola de fogo aqui
                    int randomRoll = random.nextInt(50);
                    if (randomRoll > 35) {
                        enemy.addProjectile(new Projectile(enemy.getX() + 20, enemy.getY() + 60, "iceball_1", 5));
                    }
                }
            } else {
                enemy.addProjectile(new Projectile(enemy.getX() + 20, enemy.getY() + 60, "iceball_1", 5));
            }
        }

        for (Enemy enemy : enemies) {
            enemy.moveX(enemy.getxDir());
            enemy.moveY(enemy.getyDir());

            if (enemy.getX() < -20 || enemy.getX() > 650) {
                enemy.setxDir(-enemy.getxDir());
            }
            if (enemy.getY() < -20 || enemy.getY() > 200) {
                enemy.setyDir(-enemy.getyDir());
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Movimento com as setinhas do mouse
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                player.moveX(player.getSpeed());
                break;
            case KeyEvent.VK_LEFT:
                player.moveX(-player.getSpeed());
                break;
            case KeyEvent.VK_UP:
                player.moveY(-player.getSpeed());
                break;
            case KeyEvent.VK_DOWN:
                player.moveY(player.getSpeed());
                break;
        }

        // Não permitir que o jogador saia da tela
        if (player.getX() >= 650) {
            player.setX(650);
        }
        if (player.getX() <= -15) {
            player.setX(-15);
        }
        if (player.getY() >= 520) {
            player.setY(520);
        }
        if (player.getY() <= -10) {
            player.setY(-10);
        }

        // Adicionar bolas de fogo com a barra de espaço
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            playerProjectiles.add(new Projectile(player.getX() + 25, player.getY(), "fireball_1", -20));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

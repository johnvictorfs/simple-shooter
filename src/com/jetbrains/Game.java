package com.jetbrains;

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
    private Player player = new Player(310, 520, "wizard_1");
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Fireball> playerFireballs = new ArrayList<>();
    private ArrayList<Fireball> enemyFireballs = new ArrayList<>();

    Game() {
        int delay = 8;

        enemies.add(new Enemy(110, 230, "gargoyle_1"));
        enemies.add(new Enemy(55, 140, "gargoyle_1"));
        enemies.add(new Enemy(75, 170, "gargoyle_1"));
        enemies.add(new Enemy(430, 180, "dragon_1"));
        enemies.add(new Enemy(350, 290, "dragon_1"));

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {

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
        for (Enemy enemy : enemies) {
            if (enemy.getSprite() != null) {
                g.drawImage(enemy.getSprite(), enemy.getX(), enemy.getY(), null);
            } else {
                g.drawRect(enemy.getX(), enemy.getY(), 20, 20);
            }
        }

        // Desenhar todas as bolas de fogo na tela
        for (Fireball fireball : playerFireballs) {
            if (fireball.getY() < 700) {
                // Desenhar a imagem da bola de fogo caso encontre imagem, ou um retângulo azul caso não.
                if (fireball.getSprite() != null) {
                    g.drawImage(fireball.getSprite(), fireball.getX(), fireball.getY(), null);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(fireball.getX(), fireball.getY(), 5, 5);
                }
            } else {
                playerFireballs.remove(fireball);
            }
        }

        g.dispose();

        // TODO: Mostrar controles do jogo quando ele ainda não tiver começado
    }

    private void moveRight() {
        player.moveX(20);
    }

    private void moveLeft() {
        player.moveX(-20);
    }

    private void moveUp() {
        player.moveY(-20);
    }

    private void moveDown() {
        player.moveY(20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        // Mover as balas pra cima
        for (Fireball fireball : playerFireballs) {
            fireball.moveY(fireball.getSpeed());
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Movimento pelas setinhas
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Não permitir que o Player ultrapasse as bordas
            if (player.getX() >= 650) {
                player.setX(650);
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (player.getX() <= 10) {
                player.setX(10);
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (player.getY() <= 10) {
                player.setY(10);
            } else {
                moveUp();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (player.getY() >= 530) {
                player.setY(530);
            } else {
                moveDown();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            playerFireballs.add(new Fireball(player.getX() + 25, player.getY(), "fireball_1"));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

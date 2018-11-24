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
    private Player player = new Player(310, 520);
    private ArrayList<Bullet> bullets = new ArrayList<>();

    Game() {
        int delay = 8;
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

        // Desenhar todas as bolas de fogo na tela
        for (Bullet bullet : bullets) {
            if (bullet.getY() < 700) {
                // Desenhar a imagem da bola de fogo caso encontre imagem, ou um retângulo azul caso não.
                if (bullet.getSprite() != null) {
                    g.drawImage(bullet.getSprite(), bullet.getX(), bullet.getY(), null);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(bullet.getX(), bullet.getY(), 5, 5);
                }
            } else {
                System.out.println("Removendo bala.");
                bullets.remove(bullet);
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
        for (Bullet bullet : bullets) {
            bullet.moveY(bullet.getSpeed());
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
            bullets.add(new Bullet(player.getX() + 25, player.getY()));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

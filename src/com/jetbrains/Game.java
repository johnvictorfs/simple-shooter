package com.jetbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Game extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;

    private int score = 0;
    private int totalEnemies = 10;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private int playerY = 520;

    public Game() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        Color BGColor = Color.gray;
        Color FGColor = Color.orange;

        // Background
        g.setColor(BGColor);
        setBackground(BGColor);
        g.fillRect(1, 1, 692, 592);

        // Bordas
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Player
        g.setColor(Color.cyan);
        g.fillRect(playerX, playerY, 20, 20);

        g.dispose();
        // Tiros
        // For loop aqui com todos os tiros na tela e as coordenadas atuais deles
//        g.setColor(Color.red);
//        g.fillRect(playerX, 550, 100, 8);

        // TODO: Mostrar controles do jogo quando ele ainda não tiver começado
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }
    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
    public void moveUp() {
        play = true;
        playerY -= 20;
    }
    public void moveDown() {
        play = true;
        playerY += 20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Movimento pelas setinhas
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Não permitir que o Player ultrapasse as bordas
            if (playerX >= 650) {
                playerX = 650;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (playerY <= 10) {
                playerY = 10;
            } else {
                moveUp();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (playerY >= 530) {
                playerY = 530;
            } else {
                moveDown();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.printf("Tiro nas coordenadas: %d %d\n", playerX, playerY);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}

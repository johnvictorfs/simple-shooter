package com.game;

import com.exceptions.EntityOutOfBoundsException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;


public class Game extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private javax.swing.Timer timer;
    private Player player = new Player(310, 520, "wizard_1", 30);
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private GameLoop loop;

    Game() {
        int delay = 8;

        enemies.add(new Enemy(100, 110, "gargoyle_1", -3, -2));
        enemies.add(new Enemy(200, 140, "gargoyle_1", -1, -1));
        enemies.add(new Enemy(300, 130, "gargoyle_1", -1, -2));
        enemies.add(new Enemy(400, 140, "dragon_1", -4, -2));
        enemies.add(new Enemy(500, 80, "dragon_1", -2, -2));

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(delay, this);
        timer.start();
        loop = new GameLoop(player, enemies);
    }


    public void paint(Graphics g) {
        // Para de desenhar na tela se o player está morto, para evitar sair da tela de Game Over
        if (player.isDead()) {
            this.play = false;
            g.setColor(Color.black);
            g.fillRect(0, 0, 900, 900);
            g.setFont(new Font("Courier New", Font.BOLD, 40));
            g.setColor(Color.red);
            g.drawString("Game Over, você morreu!", 80, 150);
            g.setFont(new Font("Courier New", Font.BOLD, 15));
            g.drawString("Pontuaçao final: " + player.getScore(), 250, 200);
            g.setColor(Color.blue);
            g.setFont(new Font("Courier New", Font.BOLD, 25));
            g.drawString("Aperte Enter para reiniciar", 140, 300);
            return;
        }

        BufferedImage background;
        try {
            background = ImageIO.read(getClass().getResource("/assets/background.png"));
        } catch (IOException e) {
            background = null;
            e.printStackTrace();
        }
        drawBackground(g, background);

        // Desenhar o jogador, desenha a imagem dele caso encontrada, um retângulo azul claro caso não.
        if (player.getSprite() != null) {
            g.drawImage(player.getSprite(), player.getX(), player.getY(), null);
        } else {
            g.setColor(Color.cyan);
            g.fillRect(player.getX(), player.getY(), 20, 20);
        }

        // Desenhar todos os inimigos presentes
        for (Enemy enemy : enemies) {
            // Continuar desenhando o inimigo apenas se ele estiver vivo
            // mas continuar desenhando os projeteis que ele ja lancou mesmo se estiver morto
            if (!enemy.isDead()) {
                if (enemy.getSprite() != null) {
                    g.drawImage(enemy.getSprite(), enemy.getX(), enemy.getY(), null);
                } else {
                    g.setColor(Color.orange);
                    g.drawRect(enemy.getX(), enemy.getY(), 32, 32);
                }
                if (enemy.intersects(player)) {
                    player.death();
                    loop.stopRunning();
                    return;
                }
                // Matar inimigos com os projeteis do jogador
                Iterator<Projectile> pProjectileIterator = player.getProjectiles().iterator();
                while (pProjectileIterator.hasNext()) {
                    Projectile projectile = pProjectileIterator.next();
                    if (projectile.intersects(enemy)) {
                        player.addScore();
                        enemy.death();
                        pProjectileIterator.remove();
                    }
                }
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
                if (projectile.intersects(player)) {
                    player.death();
                    loop.stopRunning();
                    return;
                }
                // Remover projeteis que sairam do mapa
                if (projectile.getY() > 700) {
                    eProjectileIterator.remove();
                }
            }
        }

        // Desenhar todas as bolas de fogo na tela
        Iterator<Projectile> pProjectileIter = player.getProjectiles().iterator();
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
        g.setColor(Color.black);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Pontos: " + player.getScore(), 10, 20);

        if (!play) {
            g.setColor(Color.black);
            g.setFont(new Font("Courier New", Font.BOLD, 25));
            g.drawString("Aperte Enter para iniciar", 150, 300);
            g.setColor(Color.orange);
            g.drawString("- Controles - ", 190, 350);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Setinhas: Andar", 220, 370);
            g.drawString("Espaço: Atirar", 220, 385);
        }

        // Mostrar que jogador venceu se não existir mais nenhum inimigo
        boolean enemiesLeft = false;
        for (Enemy enemy : enemies) {
            if (!enemy.isDead()) {
                enemiesLeft = true;
                break;
            }
        }
        if (!enemiesLeft) {
            play = false;
            //loop.stopRunning();
            BufferedImage bgWinImgPath;
            try {
                bgWinImgPath = ImageIO.read(getClass().getResource("/assets/background_2.png"));
            } catch (IOException e) {
                bgWinImgPath = null;
                e.printStackTrace();
            }
            drawBackground(g, bgWinImgPath);
            g.setColor(Color.orange);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Você ganhou! - Pontuação: " + player.getScore(), 200, 50);
            g.drawString("Aperte Enter para reiniciar", 220, 90);
        }
        g.dispose();
    }

    private void drawBackground(Graphics g, BufferedImage bgWin) {
        if (bgWin != null) {
            g.drawImage(bgWin, 0, 0, 700, 600, null);
        } else {
            g.setColor(Color.darkGray);
            g.drawRect(0, 0, 700, 600);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!play) {
            // Iniciar (ou reiniciar) o jogo sempre que apertar Enter
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                this.play = true;
                // Reposicionar os inimigos pras posicioes iniciais e deletar seus tiros, pra nao matar instantaneamente
                // o jogador depois de reiniciar
                for (Enemy enemy : enemies) {
                    enemy.setX(enemy.getInitialX());
                    enemy.setY(enemy.getInitialY());
                    enemy.getProjectiles().clear();
                    enemy.setAlive();
                }
                player.setX(player.getInitialX());
                player.setY(player.getInitialY());
                player.getProjectiles().clear();
                player.resetScore();
                player.setAlive();

                if (loop.getState() == Thread.State.NEW) {
                    System.out.println("Starting game.");
                    loop.setRunning();
                    loop.start();
                } else {
                    System.out.println("Restarting game.");
                    loop.setRunning();
                }
            }
        }
        if (play) {
            // Movimento com as setinhas do mouse
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    try {
                        player.moveX(player.getSpeed());
                    } catch (EntityOutOfBoundsException e1) {
                        player.setX(619);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    try {
                        player.moveX(-player.getSpeed());
                    } catch (EntityOutOfBoundsException e1) {
                        player.setX(-15);
                    }
                    break;
                case KeyEvent.VK_UP:
                    try {
                        player.moveY(-player.getSpeed());
                    } catch (EntityOutOfBoundsException e1) {
                        player.setY(-10);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    try {
                        player.moveY(player.getSpeed());
                    } catch (EntityOutOfBoundsException e1) {
                        player.setY(519);
                    }
                    break;
            }

            // Não permitir que o jogador saia da tela
//            if (player.getX() >= 650) {
//                player.setX(650);
//            }
//            if (player.getX() <= -15) {
//                player.setX(-15);
//            }
//            if (player.getY() >= 520) {
//                player.setY(520);
//            }
//            if (player.getY() <= -10) {
//                player.setY(-10);
//            }

            // Adicionar bolas de fogo com a barra de espaço
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                player.addProjectile(new Projectile(player.getX() + 25, player.getY(), "fireball_1", -20));
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

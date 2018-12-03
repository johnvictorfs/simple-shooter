package com.game;

import com.exceptions.EntityOutOfBoundsException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private Player player;
    private ArrayList<Enemy> enemies;
    private GameLoop loop;

    Game() {
        String playerSavePath = "player_save.dat";
        player = new Player(310, 520, "wizard_1", 30, 0);
        player.readSave(playerSavePath);

        enemies = new ArrayList<>();
        enemies.add(new Enemy(100, 110, "gargoyle_1", -3, -2, 2));
        enemies.add(new Enemy(200, 140, "gargoyle_1", -1, -1, 2));
        enemies.add(new Enemy(300, 130, "gargoyle_1", -1, -2, 2));
        enemies.add(new Enemy(400, 140, "dragon_1", -4, -2, 2));
        enemies.add(new Enemy(500, 80, "dragon_1", -2, -2, 4));

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(8, this);
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
            g.drawString("Pontuaçao final (esse jogo): " + player.getScore(), 250, 200);
            g.drawString("Pontuaçao total: " + player.getTotalScore(), 250, 220);
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
            player.getSprite().paintIcon(this, g, player.getX(), player.getY());
        } else {
            g.setColor(Color.cyan);
            g.fillRect(player.getX(), player.getY(), 32, 32);
        }

        // Desenhar todos os inimigos presentes
        for (Enemy enemy : enemies) {
            // Continuar desenhando o inimigo apenas se ele estiver vivo
            // mas continuar desenhando os projeteis que ele ja lancou mesmo se estiver morto
            if (!enemy.isDead()) {
                if (enemy.getSprite() != null) {
                    enemy.getSprite().paintIcon(this, g, enemy.getX(), enemy.getY());
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
                        player.addScore(enemy.getScore());
                        player.writeSave();
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
                    projectile.getSprite().paintIcon(this, g, projectile.getX(), projectile.getY());
                } else {
                    g.setColor(Color.green);
                    g.drawRect(projectile.getX(), projectile.getY(), 10, 10);
                }
                // Matar o jogador com os projeteis dos inimigos
                if (projectile.intersects(player)) {
                    player.death();
                    loop.stopRunning();
                    return;
                }
                // Remover projeteis que sairam do mapa
                if (projectile.isDisabled()) {
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
                    projectile.getSprite().paintIcon(this, g, projectile.getX(), projectile.getY());
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(projectile.getX(), projectile.getY(), 10, 10);
                }
            } else {
                pProjectileIter.remove();
            }
        }
        g.setColor(Color.black);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Pontos (esse jogo): " + player.getScore(), 10, 20);
        g.drawString("Pontos (total): " + player.getTotalScore(), 10, 35);

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
            BufferedImage bgWinImgPath;
            try {
                bgWinImgPath = ImageIO.read(getClass().getResource("/assets/background_2.png"));
            } catch (IOException e) {
                bgWinImgPath = null;
                e.printStackTrace();
            }
            drawBackground(g, bgWinImgPath);
            g.setColor(Color.green);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Você ganhou!", 200, 30);
            g.setColor(Color.darkGray);
            g.drawString("Pontuação esse jogo: " + player.getScore(), 200, 65);
            g.drawString("Pontuação total: " + player.getTotalScore(), 200, 90);
            g.setColor(Color.magenta);
            g.drawString("Aperte Enter para reiniciar", 220, 125);
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
                    System.out.println("Iniciando jogo.");
                    loop.setRunning();
                    loop.start();
                } else {
                    System.out.println("Reiniciando jogo.");
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
                        player.setX(664);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    try {
                        player.moveX(-player.getSpeed());
                    } catch (EntityOutOfBoundsException e1) {
                        player.setX(1);
                    }
                    break;
                case KeyEvent.VK_UP:
                    try {
                        player.moveY(-player.getSpeed());
                    } catch (EntityOutOfBoundsException e1) {
                        player.setY(1);
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

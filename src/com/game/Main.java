package com.game;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame app = new JFrame();
        Game game = new Game();
        app.setBounds(10, 10, 700, 600);
        app.setLocationRelativeTo(null);  // Faz o app iniciar no centro
        app.setTitle("Shooter");
        app.setResizable(false);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(game);
    }
}

// TODO: Adicionar exceçoes criadas por mim
// TODO: Usar throw/try/catch com essas exceçoes para controlar o flow do jogo
// TODO: Ler/Escrever algum tipo de dado proprio do jogo para arquivos

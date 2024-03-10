package main;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static JFrame window;
    public static void main(String[] args) throws IOException {
        //System.setProperty("sun.java2d.opengl", "true");
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("D9 Slayer");
      //  window.setUndecorated(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}

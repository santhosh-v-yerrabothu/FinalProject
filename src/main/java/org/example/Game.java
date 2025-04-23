package org.example;

import javax.swing.*;

public class Game {
    private static JFrame gameFrame;

    public static JFrame initializeGame() {
        gameFrame = new JFrame("Bubble Game!!");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT); // width matters here

        // Main container with horizontal BoxLayout
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        container.add(GameFieldUI.getGameCanvas());
        container.add(GameControlUI.getPanel());

        gameFrame.add(container);
        return gameFrame;
    }
}

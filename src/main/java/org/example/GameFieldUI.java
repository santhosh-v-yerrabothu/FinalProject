package org.example;

import java.awt.*;

public class GameFieldUI {
    private static GameCanvas gameCanvas = new GameCanvas();

    static {
        gameCanvas.setPreferredSize(new Dimension(Constants.GAME_FIELD_WIDTH, Constants.GAME_HEIGHT)); // 80% of 800
        gameCanvas.setMaximumSize(new Dimension(Constants.GAME_FIELD_WIDTH, Integer.MAX_VALUE));
        gameCanvas.setMinimumSize(new Dimension(Constants.GAME_FIELD_WIDTH, 0));
        gameCanvas.addMouseListener(new MouseListener());
    }

    public static GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public static void refreshUI() {
        gameCanvas.repaint();
    }
}

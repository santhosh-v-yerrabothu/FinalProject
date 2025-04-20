package org.example;

import java.awt.*;

public class GameFieldUI {
    private static GameCanvas gameCanvas = new GameCanvas();

    static {
        gameCanvas.setPreferredSize(new Dimension(640, 400)); // 80% of 800
        gameCanvas.setMaximumSize(new Dimension(640, Integer.MAX_VALUE));
        gameCanvas.setMinimumSize(new Dimension(640, 0));
        gameCanvas.addMouseListener(new MouseListener());
    }

    public static GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public static void refreshUI() {
        gameCanvas.repaint();
    }
}

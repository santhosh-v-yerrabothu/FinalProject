package org.example;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends Canvas {

    private final int DEFAULT_RADIUS = 20;
    private boolean isMessageShown = false;

    @Override
    public void paint(Graphics graphics) {
        GameData gameData = GameData.getInstance();
        System.out.println("Loading Canvas");
        if(gameData.getRound() == 0) {
            System.out.println("Step1");
            return;
        } else if(gameData.getRound() == 1) {
            System.out.println("Step2");
            if(gameData.getRoundOneCoOrdinates().size() == 0 && !isMessageShown) {
                JOptionPane.showInternalMessageDialog(null, "Please select "+ gameData.getLevel()+" points on White Canvas",
                        "Input", JOptionPane.INFORMATION_MESSAGE);
                isMessageShown = true;
                return;
            } else {
                System.out.println("Step3");
                graphics.setColor(Color.BLACK);
                for (Integer[] points : gameData.getRoundOneCoOrdinates()) {
                    graphics.drawOval(points[0] - (DEFAULT_RADIUS / 2),
                            points[1] - (DEFAULT_RADIUS / 2), DEFAULT_RADIUS, DEFAULT_RADIUS);
                }
                return;
            }
        } else {
            System.out.println("Step4");
            return;
        }
    }
}

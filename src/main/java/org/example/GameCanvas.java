package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GameCanvas extends Canvas {

    private final int DEFAULT_RADIUS = 10;
    private final int DEFAULT_NEIGHBORHOOD = 50;
    private boolean isMessageShown = false;

    @Override
    public void paint(Graphics graphics) {
        GameData gameData = GameData.getInstance();
        System.out.println("Loading Canvas");
        if(gameData.getRound() == 0) {
            return;
        } else if(gameData.getRound() == 1) {
            List<Integer[]> coords = gameData.getRoundOneCoOrdinates();
            if(coords.size() == 0 && !isMessageShown) {
                JOptionPane.showInternalMessageDialog(null, "Please select "+ gameData.getDifficultyLevel()+" points on White Canvas",
                        "Input", JOptionPane.INFORMATION_MESSAGE);
                isMessageShown = true;
                return;
            } else {
                int currentRound = gameData.getRound();
                int neighborhoodSize = DEFAULT_NEIGHBORHOOD + (currentRound-1)*18;
                System.out.println("Drawing Bubbles for round: " + currentRound);
                graphics.setColor(Color.BLACK);
                for (Integer[] points : coords) {
                    graphics.drawRect(points[0]-(neighborhoodSize/2), points[1]-(neighborhoodSize/2), neighborhoodSize, neighborhoodSize);
                    if(!gameData.isBurstingForGivenRoundStarted()) {
                        System.out.println("Not using random co-ordinates");
                        graphics.drawOval(points[0] - (DEFAULT_RADIUS / 2),
                                points[1] - (DEFAULT_RADIUS / 2), DEFAULT_RADIUS, DEFAULT_RADIUS);
                    } else {
                        int[] randomCircleCoOrdinates = getRandomCoOrdinatesInNeighborhood(points[0], points[1], neighborhoodSize, DEFAULT_RADIUS);
                        System.out.println("using random co-ordinates" + randomCircleCoOrdinates[0] +":"+randomCircleCoOrdinates[1]);
                        graphics.drawOval(randomCircleCoOrdinates[0],
                                randomCircleCoOrdinates[1], DEFAULT_RADIUS, DEFAULT_RADIUS);
                    }
                }
                return;
            }
        } else {
            System.out.println("Executing Other rounds code");
            return;
        }
    }

    private int[] getRandomCoOrdinatesInNeighborhood(int x, int y, int neighborhoodSize, int radius) {
        int x_min =  x-(neighborhoodSize/2)+radius;
        int x_max =  x+(neighborhoodSize/2)-radius;
        int y_min =  y-(neighborhoodSize/2)+radius;
        int y_max =  y+(neighborhoodSize/2)-radius;
        Random random = new Random();
        int[] randomCoOrdinates = { random.nextInt((x_max - x_min) + 1) + x_min, random.nextInt((y_max - y_min) + 1) + y_min};
        return randomCoOrdinates;
//        new Timer(2000, e -> {
//            int x_random = random.nextInt((x_max - x_min) + 1) + x_min;
//            int y_random = random.nextInt((y_max - y_min) + 1) + y_min;
//            moveCircle(x_random, y_random);
//        }).start();
    }

    public void repaintingOnTimer() {
        new Timer(1000, e -> {
            this.repaint();
        }).start();
    }
}

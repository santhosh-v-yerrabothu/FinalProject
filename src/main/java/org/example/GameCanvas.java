package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.example.Constants.DEFAULT_NEIGHBORHOOD;
import static org.example.Constants.DEFAULT_DIAMETER;

public class GameCanvas extends Canvas {

    private boolean isMessageShown = false;

    @Override
    public void paint(Graphics graphics) {
        GameData gameData = GameData.getInstance();
        System.out.println("Loading Canvas");
        if(gameData.isGameStarted()){
            graphics.setFont(new Font("SansSerif", Font.BOLD, 20));
            graphics.setColor(Color.BLACK);
            // Draw text (like a label)
            graphics.drawString("Round: " + gameData.getRound(), 200, 20);
        }
        if(gameData.getRound() == 0) {
            return;
        } else if(gameData.getRound() == 1) {
            List<Integer[]> coords = gameData.getMainCoOrdinatesForARound();
            if(coords.size() == 0 && !isMessageShown) {
                JOptionPane.showInternalMessageDialog(null, "Please select "+ gameData.getDifficultyLevel()+" points on White Canvas",
                        "Input", JOptionPane.INFORMATION_MESSAGE);
                isMessageShown = true;
                return;
            } else {
                int currentRound = gameData.getRound();
                int neighborhoodSize = Utils.getNeighborhoodSizeInARound(currentRound);
                System.out.println("Drawing Bubbles for round: " + currentRound);
                graphics.setColor(Color.BLACK);
                gameData.setRandomCoOrdinatesAssociatedWithMainCoOrdinates(new HashMap<>());
                for(int i=0; i< coords.size(); i++) {
                    Integer[] points = coords.get(i);
                    graphics.drawRect(points[0]-(neighborhoodSize/2), points[1]-(neighborhoodSize/2), neighborhoodSize, neighborhoodSize);
                    if(!gameData.isBurstingForGivenRoundStarted()) {
                        System.out.println("Not using random co-ordinates");
                        graphics.drawOval(points[0] - (DEFAULT_DIAMETER / 2),
                                points[1] - (DEFAULT_DIAMETER / 2), DEFAULT_DIAMETER, DEFAULT_DIAMETER);
                    } else {
                        Integer[] randomCircleCoOrdinates = getRandomCoOrdinatesInNeighborhood(points[0], points[1], neighborhoodSize, DEFAULT_DIAMETER);
                        Map<Integer, Integer[]> randomCoOrdinatesAssociatedWithMainCoOrdinates = gameData.getRandomCoOrdinatesAssociatedWithMainCoOrdinates();
                        randomCoOrdinatesAssociatedWithMainCoOrdinates.put(i, randomCircleCoOrdinates);
                        System.out.println("using random co-ordinates" + randomCircleCoOrdinates[0] +":"+randomCircleCoOrdinates[1]);
                        graphics.drawOval(randomCircleCoOrdinates[0],
                                randomCircleCoOrdinates[1], DEFAULT_DIAMETER, DEFAULT_DIAMETER);
                    }
                }
                return;
            }
        } else {
            System.out.println("Executing Other rounds code");
            int currentRound = gameData.getRound();
            List<Integer[]> coords = gameData.getMainCoOrdinatesForARound();
            int neighborhoodSize = Utils.getNeighborhoodSizeInARound(currentRound);
            for (int i = 0; i < coords.size(); i++) {
                Integer[] points = coords.get(i);
                graphics.drawRect(points[0] - (neighborhoodSize / 2), points[1] - (neighborhoodSize / 2), neighborhoodSize, neighborhoodSize);
                Integer[] randomCircleCoOrdinates = getRandomCoOrdinatesInNeighborhood(points[0], points[1], neighborhoodSize, DEFAULT_DIAMETER);
                Map<Integer, Integer[]> randomCoOrdinatesAssociatedWithMainCoOrdinates = gameData.getRandomCoOrdinatesAssociatedWithMainCoOrdinates();
                randomCoOrdinatesAssociatedWithMainCoOrdinates.put(i, randomCircleCoOrdinates);
                System.out.println("using random co-ordinates" + randomCircleCoOrdinates[0] + ":" + randomCircleCoOrdinates[1]);
                graphics.drawOval(randomCircleCoOrdinates[0],
                        randomCircleCoOrdinates[1], DEFAULT_DIAMETER, DEFAULT_DIAMETER);
            }
            return;
        }
    }

    private Integer[] getRandomCoOrdinatesInNeighborhood(int x, int y, int neighborhoodSize, int radius) {
        int x_min =  x-(neighborhoodSize/2)+radius;
        int x_max =  x+(neighborhoodSize/2)-radius;
        int y_min =  y-(neighborhoodSize/2)+radius;
        int y_max =  y+(neighborhoodSize/2)-radius;
        Random random = new Random();
        Integer[] randomCoOrdinates = { random.nextInt((x_max - x_min) + 1) + x_min, random.nextInt((y_max - y_min) + 1) + y_min};
        return randomCoOrdinates;
    }

    public void repaintingOnTimer() {
       Timer timer = new Timer(2000, e -> {
            this.repaint();
        });
        GameData gameData = GameData.getInstance();
        gameData.setTimerForGivenRound(timer);
        timer.start();
    }
}

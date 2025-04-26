package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.Constants.DEFAULT_DIAMETER;

public class GameCanvas extends Canvas {

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
        // If game is neither started nor ended, it means we just loaded it.
        if((!gameData.isGameStarted()) && (!gameData.isGameEnded())) {
            return;
        }
        // It means game started
        else if(gameData.isGameStarted()) {
            System.out.println("Game Started. Setting color");
            this.setBackground(new Color(34, 139, 34));
            if(gameData.getRound() == 1) {
                List<Integer[]> coords = gameData.getMainCoOrdinatesForARound();
                if(coords.size() == 0 && !gameData.isSelectionMessageShown()) {
                    JOptionPane.showInternalMessageDialog(null, "Please select "+ gameData.getDifficultyLevel()+" points on Green Playing Field",
                            "Input", JOptionPane.INFORMATION_MESSAGE);
                    gameData.setSelectionMessageShown(true);
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
                        // Drawing static circles for Round 1 (before game starts)
                        if(!gameData.isBurstingForGivenRoundStarted()) {
                            System.out.println("Not using random co-ordinates");
                            graphics.drawOval(points[0] - (DEFAULT_DIAMETER / 2),
                                    points[1] - (DEFAULT_DIAMETER / 2), DEFAULT_DIAMETER, DEFAULT_DIAMETER);
                        }
                        // Drawing bouncing circles (Game started)
                        else {
                            // Displaying Timer
                            graphics.setFont(new Font("SansSerif", Font.BOLD, 20));
                            graphics.setColor(Color.BLACK);
                            graphics.drawString("Timer: " + gameData.getGameTime(), 600, 20);
                            drawBouncingCircles(points, neighborhoodSize, graphics, i);
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

                    graphics.setFont(new Font("SansSerif", Font.BOLD, 20));
                    graphics.setColor(Color.BLACK);
                    graphics.drawString("Timer: " + gameData.getGameTime(), 600, 20);

                    Integer[] points = coords.get(i);
                    graphics.drawRect(points[0] - (neighborhoodSize / 2), points[1] - (neighborhoodSize / 2), neighborhoodSize, neighborhoodSize);
                    drawBouncingCircles(points, neighborhoodSize, graphics, i);
                }
                return;
            }
        }
        else if(gameData.isGameEnded()) {
            System.out.println("Game is completed");
            return;
        }
    }

    public void repaintingOnTimer() {
       GameData gameData = GameData.getInstance();
       int screenRefreshTime = gameData.getRound() == 1? (Constants.REFRESH_SCREEN_IN_MICROSECONDS/2) : Constants.REFRESH_SCREEN_IN_MICROSECONDS;
       Timer timer = new Timer(screenRefreshTime, e -> {
            int currentGameTime = gameData.getGameTime();
            int newTime = currentGameTime - (Constants.TIME_LOSS_PER_SCREEN_REFRESH_IN_SECONDS);
            if(newTime <= 0) {
                System.out.println("Time is up!!");
                gameData.gameCompleted();
                this.repaint();
                JOptionPane.showInternalMessageDialog(null, "Game Over!! Time is up!!",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                gameData.setGameTime(newTime);
                this.repaint();
            }

        });
       gameData.setRefreshTimerForGivenRound(timer);
       timer.start();

    }

    private void drawBouncingCircles(Integer[] points, int neighborhoodSize, Graphics graphics, int index) {
        GameData gameData = GameData.getInstance();
        Integer[] randomCircleCoOrdinates = gameData.getRandomCoOrdinatesInNeighborhood(points[0], points[1], neighborhoodSize, DEFAULT_DIAMETER);
        Map<Integer, Integer[]> randomCoOrdinatesAssociatedWithMainCoOrdinates = gameData.getRandomCoOrdinatesAssociatedWithMainCoOrdinates();
        randomCoOrdinatesAssociatedWithMainCoOrdinates.put(index, randomCircleCoOrdinates);
        System.out.println("using random co-ordinates" + randomCircleCoOrdinates[0] + ":" + randomCircleCoOrdinates[1]);
        graphics.drawOval(randomCircleCoOrdinates[0],
                randomCircleCoOrdinates[1], DEFAULT_DIAMETER, DEFAULT_DIAMETER);
    }
}

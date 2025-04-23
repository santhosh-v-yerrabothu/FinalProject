package org.example;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MouseListener implements java.awt.event.MouseListener {

    @Override
    public void mouseClicked(MouseEvent event) {
        GameData gameData = GameData.getInstance();
        GameCanvas myCanvas = (GameCanvas) event.getSource();
        if(gameData.isGameStarted()) {
            if(gameData.getRound() == 1) {
                if(!gameData.isBurstingForGivenRoundStarted()) {
                    // This means we are pointing co-ordinates for round 1
                    if(gameData.getMainCoOrdinatesForARound().size() < gameData.getDifficultyLevel()) {
                        int currentSize = gameData.getMainCoOrdinatesForARound().size();
                        gameData.getMainCoOrdinatesForARound().add(currentSize, new Integer[] { event.getX(), event.getY() });
                        System.out.println("Added Circle at index" + currentSize);
                        //GameCanvas myCanvas = (GameCanvas) event.getSource();
                        myCanvas.repaint();
                        if(gameData.getMainCoOrdinatesForARound().size() == gameData.getDifficultyLevel()) {
                            JOptionPane.showInternalMessageDialog(null, "Starting game...",
                                    "Input", JOptionPane.INFORMATION_MESSAGE);
                            gameData.setBurstingForGivenRoundStarted(true);
                            myCanvas.repaintingOnTimer();
                        }
                    } else if(gameData.getMainCoOrdinatesForARound().size() == gameData.getDifficultyLevel()) {
                        System.out.println("Bubble Bursting Started...");
                        gameData.setBurstingForGivenRoundStarted(true);
                    }
                }
                // This means we are bursting bubbles for round 1
                else {
                    burstBubbles(event);
                }
            } // This means we are bursting bubbles for round 2
            else {
                burstBubbles(event);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private int IsSelectedPointInsideCircle(int selectedX, int selectedY) {
       GameData gameData = GameData.getInstance();
       Map<Integer, Integer[]> randomCoOrdinates = gameData.getRandomCoOrdinatesAssociatedWithMainCoOrdinates();
       for(Map.Entry<Integer, Integer[]> entry: randomCoOrdinates.entrySet()) {
           Integer[] circleCoOrdinates = (Integer[]) entry.getValue();
           double distance = Math.pow((circleCoOrdinates[0] - selectedX), 2) + Math.pow((circleCoOrdinates[1]-selectedY), 2);
           boolean isInside = distance <= Math.pow(Constants.DEFAULT_DIAMETER, 2);
           if(isInside) {
               return entry.getKey().intValue();
           }
       }
       return -1;
    }

    private List<Integer[]> generateRandomCoOrdinatesForARound(int round) {
        GameData gameData = GameData.getInstance();
        // We have exclude this area from width so that neighborhoods won't go outside the field. Two is just a buffer
        int fieldWidthMin = (Utils.getNeighborhoodSizeInARound(round)/2) + 2;
        int fieldWidthMax = Constants.GAME_FIELD_WIDTH - (fieldWidthMin);
        // 20 is pixels we used to display Round Number. A neighborhood cannot stretch into that label
        int fieldHeightMin = (Utils.getNeighborhoodSizeInARound(round)/2) + 2 + 20;
        int fieldHeighMax = Constants.GAME_FIELD_HEIGHT - ((Utils.getNeighborhoodSizeInARound(round)/2) + 2);
        Random random = new Random();
        List<Integer[]> randomCoOrdinates = new ArrayList<>();
        for(int i=0; i<gameData.getDifficultyLevel(); i++) {
            boolean insideNeighborhood = true;
            int randomX = -1;
            int randomY = -1;
            while(insideNeighborhood) {
                randomX = random.nextInt((fieldWidthMax - fieldWidthMin) + 1) + fieldWidthMin;
                randomY = random.nextInt((fieldHeighMax - fieldHeightMin) + 1) + fieldHeightMin;
                insideNeighborhood = checkIfNewCoOrdinatesAreInNeighborhoodOfOtherCoOrdinates(randomX, randomY, randomCoOrdinates, round);
                System.out.println("Generated point is inside neighborhood. Generating again");
            }
            randomCoOrdinates.add(new Integer[] {randomX, randomY});
        }
        System.out.println("Co-ordinates generated for round: " + round + "::"+ randomCoOrdinates);
        return randomCoOrdinates;
    }

    private boolean checkIfNewCoOrdinatesAreInNeighborhoodOfOtherCoOrdinates(int x, int y, List<Integer[]> otherCoOrdinates, int currentRound) {

        for(Integer[] point : otherCoOrdinates) {
            System.out.println("Generated : " + x + " : " + y + " is compared against :" + point[0] + " : " + point[1]);
            double pointsDistance = Math.pow(point[0]-x, 2) + Math.pow(point[1]-y, 2);
            double expected = Math.pow(Utils.getNeighborhoodHypotenuse(currentRound), 2);
            System.out.println("Points distance " + pointsDistance);
            System.out.println("expected " + expected);
            boolean response =  pointsDistance<= expected;
            if(response) {
                return true;
            }
        }
        return false;
    }

    private void burstBubbles(MouseEvent event) {
        GameData gameData = GameData.getInstance();
        Timer currentRoundTimer = gameData.getTimerForGivenRound();
        if(currentRoundTimer.isRunning()) {
            currentRoundTimer.stop();
        }
        int bubbleIndexToRemove = this.IsSelectedPointInsideCircle(event.getX(), event.getY());
        if(bubbleIndexToRemove >= 0) {
            gameData.getMainCoOrdinatesForARound().remove(bubbleIndexToRemove);
            // If we are removing last bubble, we need to start next round
            if(gameData.getMainCoOrdinatesForARound().size() == 0) {
                GameCanvas myCanvas = (GameCanvas) event.getSource();
                myCanvas.repaint();
                currentRoundTimer.stop();
                gameData.setRound(gameData.getRound()+1);
                gameData.setTimerForGivenRound(null);
                gameData.setBurstingForGivenRoundStarted(false);
                // Initialize data for second round
                gameData.setMainCoOrdinatesForARound(generateRandomCoOrdinatesForARound(gameData.getRound()));
                gameData.setGameStarted(true);
                myCanvas.repaintingOnTimer();
            } else {
                currentRoundTimer.start();
            }
        }
        // we are returning -1 if clicked co-ordinate is not inside any circle
        else {
            System.out.println("Game Over !!!!!!!!!");
        }
    }
}

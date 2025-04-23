package org.example;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
                            gameData.setGameTime(Utils.getTimeForCurrentRound(gameData.getRound()));
                            myCanvas.repaintingOnTimer();
                        }
                    } else if(gameData.getMainCoOrdinatesForARound().size() == gameData.getDifficultyLevel()) {
                        System.out.println("Bubble Bursting Started...");
                        gameData.setBurstingForGivenRoundStarted(true);
                        gameData.setGameTime(Utils.getTimeForCurrentRound(gameData.getRound()));
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



    private void burstBubbles(MouseEvent event) {
        GameData gameData = GameData.getInstance();
        GameCanvas myCanvas = (GameCanvas) event.getSource();
        Timer currentRoundTimer = gameData.getRefreshTimerForGivenRound();
        if(currentRoundTimer.isRunning()) {
            currentRoundTimer.stop();
        }
        int bubbleIndexToRemove = gameData.IsSelectedPointInsideCircle(event.getX(), event.getY());
        if(bubbleIndexToRemove >= 0) {
            if(bubbleIndexToRemove < gameData.getMainCoOrdinatesForARound().size()) {
                // To avoid array out of bounds exception
                gameData.getMainCoOrdinatesForARound().remove(bubbleIndexToRemove);
            }
            gameData.getRandomCoOrdinatesAssociatedWithMainCoOrdinates().remove(bubbleIndexToRemove);
            // If we are removing last bubble, we need to start next round
            if(gameData.getMainCoOrdinatesForARound().size() == 0) {
                myCanvas.repaint();
                currentRoundTimer.stop();
                if(gameData.getRound() == Constants.LAST_ROUND_NUMBER) {
                    // game is completed successfully
                    gameData.gameCompleted();
                    JOptionPane.showInternalMessageDialog(null, "You have successfully finished the game!!!",
                            "Response", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                gameData.setRound(gameData.getRound()+1);
                gameData.setRefreshTimerForGivenRound(null);
                gameData.setBurstingForGivenRoundStarted(false);
                // Initialize data for second round
                gameData.setMainCoOrdinatesForARound(gameData.generateRandomCoOrdinatesForARound(gameData.getRound()));
                gameData.setGameStarted(true);
                gameData.setGameTime(Utils.getTimeForCurrentRound(gameData.getRound()));
                myCanvas.repaintingOnTimer();
            } else {
                currentRoundTimer.start();
            }
        }
        // we are returning -1 if clicked co-ordinate is not inside any circle
        else {
            System.out.println("Game Over !!!!!!!!!");
            gameData.gameCompleted();
            myCanvas.repaint();
            JOptionPane.showInternalMessageDialog(null, "Game Over!! You clicked outside the circle!!",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
}

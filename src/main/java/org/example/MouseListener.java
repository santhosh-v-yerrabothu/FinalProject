package org.example;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    @Override
    public void mouseClicked(MouseEvent event) {
        GameData gameData = GameData.getInstance();
        if(gameData.isGameStarted()) {
            if(gameData.getRound() == 1) {
                if(!gameData.isBurstingForGivenRoundStarted()) {
                    // This means we are pointing co-ordinates for round 1
                    if(gameData.getRoundOneCoOrdinates().size() < gameData.getDifficultyLevel()) {
                        gameData.getRoundOneCoOrdinates().add(new Integer[] { event.getX(), event.getY() });
                        GameCanvas myCanvas = (GameCanvas) event.getSource();
                        myCanvas.repaint();
                        if(gameData.getRoundOneCoOrdinates().size() == gameData.getDifficultyLevel()) {
                            JOptionPane.showInternalMessageDialog(null, "Starting game...",
                                    "Input", JOptionPane.INFORMATION_MESSAGE);
                            gameData.setBurstingForGivenRoundStarted(true);
                            myCanvas.repaintingOnTimer();
                        }
                    } else if(gameData.getRoundOneCoOrdinates().size() == gameData.getDifficultyLevel()) {
                        System.out.println("Bubble Bursting Started...");
                        gameData.setBurstingForGivenRoundStarted(true);
                    }
                }
                // This means we are bursting bubbles
                else {

                }
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
}

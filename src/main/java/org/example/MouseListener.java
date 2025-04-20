package org.example;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    @Override
    public void mouseClicked(MouseEvent event) {
        GameData gameData = GameData.getInstance();
        if(gameData.isGameStarted()) {
            // This means we are pointing co-ordinates for round 1
            if(gameData.getRoundOneCoOrdinates().size() < gameData.getLevel()) {
                gameData.getRoundOneCoOrdinates().add(new Integer[] { event.getX(), event.getY() });
                GameCanvas myCanvas = (GameCanvas) event.getSource();
                myCanvas.repaint();
            }
            // This means we are bursting bubbles
            else {

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

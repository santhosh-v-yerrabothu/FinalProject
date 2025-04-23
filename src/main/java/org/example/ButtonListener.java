package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonType = e.getActionCommand();
        GameData gameData = GameData.getInstance();
        System.out.println("Start clicked!!!");
        gameData.setRound(1);
        gameData.setGameStarted(true);
        GameFieldUI.refreshUI();
        JButton startButton = ((JButton) e.getSource());
        JPanel parentPanel = (JPanel) startButton.getParent();
        gameData.disableAllControls(parentPanel);

    }

}

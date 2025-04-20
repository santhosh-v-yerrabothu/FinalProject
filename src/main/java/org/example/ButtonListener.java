package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonType = e.getActionCommand();
        if(buttonType.equals("Start")) {
            GameData gameData = GameData.getInstance();
            System.out.println("Start clicked!!!");
            gameData.setRound(1);
            gameData.setGameStarted(true);
            GameFieldUI.refreshUI();
        } else if(buttonType.equals("Restart")) {
            System.out.println("Restart clicked!!!");
        }
    }
}

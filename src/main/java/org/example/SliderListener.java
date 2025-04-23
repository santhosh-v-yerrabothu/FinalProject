package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {

    private GameData gameData;
    public void stateChanged(ChangeEvent e) {
        gameData = GameData.getInstance();
        if(gameData.isGameStarted()) {
            System.out.println("Game already started. So, ignoring event");
            return;
        }
        JSlider source = (JSlider) e.getSource();
        int value = source.getValue();
        gameData.setDifficultyLevel(value);
        System.out.println("Slider value: " + GameData.getInstance().getDifficultyLevel());
    }
}

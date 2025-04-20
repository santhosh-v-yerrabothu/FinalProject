package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {

    private GameData gameData;
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int value = source.getValue();
        GameData.getInstance().setLevel(value);
        System.out.println("Slider value: " + GameData.getInstance().getLevel());
    }
}

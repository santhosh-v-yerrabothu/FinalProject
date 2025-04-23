package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class GameControlUI {

    private static JPanel gameControlPanel = new JPanel();

    static {
        GameData gameData = GameData.getInstance();
        gameControlPanel.setBackground(Color.ORANGE);
        gameControlPanel.setPreferredSize(new Dimension(200, 400)); // 20% of 800
        gameControlPanel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
        gameControlPanel.setMinimumSize(new Dimension(200, 0));
        gameControlPanel.setLayout(new BorderLayout());

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ButtonListener());
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ButtonListener());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.ORANGE);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        buttonsPanel.add(startButton);
        buttonsPanel.add(Box.createHorizontalStrut(5));
        buttonsPanel.add(restartButton);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        JSlider slider = new JSlider(4, 6, 4); // Start at Medium
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.addChangeListener(new SliderListener());

        // Create custom labels for each tick
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(4, new JLabel("Easy"));
        labelTable.put(5, new JLabel("Medium"));
        labelTable.put(6, new JLabel("Hard"));
        slider.setLabelTable(labelTable);

        JPanel switchesPanel = new JPanel();
        switchesPanel.setBackground(Color.ORANGE);
        switchesPanel.setLayout(new BoxLayout(switchesPanel, BoxLayout.Y_AXIS));
        if(gameData.isGameStarted()){
            JLabel label = new JLabel("Round: " + gameData.getRound());
            switchesPanel.add(label);
        }
        switchesPanel.add(buttonsPanel);
        switchesPanel.add(Box.createVerticalStrut(50));
        switchesPanel.add(slider);

        gameControlPanel.add(switchesPanel, BorderLayout.NORTH);
    }

    public static JPanel getPanel() {
        return gameControlPanel;
    }

    public void refreshUI() {

    }
}

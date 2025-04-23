package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    private int difficultyLevel = 4;
    private int round = 0;
    private boolean gameStarted = false;
    private boolean burstingForGivenRoundStarted = false;
    private Timer timerForGivenRound;
    private List<Integer []> mainCoOrdinatesForARound = new ArrayList<>();
    private Map<Integer, Integer[]> randomCoOrdinatesAssociatedWithMainCoOrdinates = new HashMap<>();

    public List<Integer[]> getMainCoOrdinatesForARound() {
        return mainCoOrdinatesForARound;
    }

    public void setMainCoOrdinatesForARound(List<Integer[]> mainCoOrdinatesForARound) {
        this.mainCoOrdinatesForARound = mainCoOrdinatesForARound;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    private static GameData _instance;

    private GameData() {

    }
    public static GameData getInstance() {
        if(_instance == null) {
            _instance = new GameData();
        }
        return _instance;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isBurstingForGivenRoundStarted() {
        return burstingForGivenRoundStarted;
    }

    public void setBurstingForGivenRoundStarted(boolean burstingForGivenRoundStarted) {
        this.burstingForGivenRoundStarted = burstingForGivenRoundStarted;
    }

    public Timer getTimerForGivenRound() {
        return timerForGivenRound;
    }

    public void setTimerForGivenRound(Timer timerForGivenRound) {
        this.timerForGivenRound = timerForGivenRound;
    }

    public Map<Integer, Integer[]> getRandomCoOrdinatesAssociatedWithMainCoOrdinates() {
        return randomCoOrdinatesAssociatedWithMainCoOrdinates;
    }

    public void setRandomCoOrdinatesAssociatedWithMainCoOrdinates(Map<Integer, Integer[]> randomCoOrdinatesAssociatedWithMainCoOrdinates) {
        this.randomCoOrdinatesAssociatedWithMainCoOrdinates = randomCoOrdinatesAssociatedWithMainCoOrdinates;
    }
}

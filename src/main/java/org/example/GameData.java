package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private int difficultyLevel = 4;
    private int round = 0;
    private boolean gameStarted = false;
    private boolean burstingForGivenRoundStarted = false;
    private List<Integer []> roundOneCoOrdinates = new ArrayList<>();

    public List<Integer[]> getRoundOneCoOrdinates() {
        return roundOneCoOrdinates;
    }

    public void setRoundOneCoOrdinates(List<Integer[]> roundOneCoOrdinates) {
        this.roundOneCoOrdinates = roundOneCoOrdinates;
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
}

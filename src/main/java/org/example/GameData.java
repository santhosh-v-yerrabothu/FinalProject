package org.example;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameData {
    private int difficultyLevel = 4;
    private int round = 0;
    private boolean gameStarted = false;
    private boolean burstingForGivenRoundStarted = false;
    private Timer refreshTimerForGivenRound;
    private int gameTime;
    private boolean gameEnded = false;
    private boolean isSelectionMessageShown = false;

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

    public Timer getRefreshTimerForGivenRound() {
        return refreshTimerForGivenRound;
    }

    public void setRefreshTimerForGivenRound(Timer refreshTimerForGivenRound) {
        this.refreshTimerForGivenRound = refreshTimerForGivenRound;
    }

    public Map<Integer, Integer[]> getRandomCoOrdinatesAssociatedWithMainCoOrdinates() {
        return randomCoOrdinatesAssociatedWithMainCoOrdinates;
    }

    public void setRandomCoOrdinatesAssociatedWithMainCoOrdinates(Map<Integer, Integer[]> randomCoOrdinatesAssociatedWithMainCoOrdinates) {
        this.randomCoOrdinatesAssociatedWithMainCoOrdinates = randomCoOrdinatesAssociatedWithMainCoOrdinates;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public boolean isSelectionMessageShown() {
        return isSelectionMessageShown;
    }

    public void setSelectionMessageShown(boolean selectionMessageShown) {
        isSelectionMessageShown = selectionMessageShown;
    }

    // Core logic Methods
    public Integer[] getRandomCoOrdinatesInNeighborhood(int x, int y, int neighborhoodSize, int radius) {
        int x_min =  x-(neighborhoodSize/2)+radius;
        int x_max =  x+(neighborhoodSize/2)-radius;
        int y_min =  y-(neighborhoodSize/2)+radius;
        int y_max =  y+(neighborhoodSize/2)-radius;
        Random random = new Random();
        Integer[] randomCoOrdinates = { random.nextInt((x_max - x_min) + 1) + x_min, random.nextInt((y_max - y_min) + 1) + y_min};
        return randomCoOrdinates;
    }

    public int IsSelectedPointInsideCircle(int selectedX, int selectedY) {
        GameData gameData = GameData.getInstance();
        Map<Integer, Integer[]> randomCoOrdinates = gameData.getRandomCoOrdinatesAssociatedWithMainCoOrdinates();
        for(Map.Entry<Integer, Integer[]> entry: randomCoOrdinates.entrySet()) {
            Integer[] circleCoOrdinates = (Integer[]) entry.getValue();
            double distance = Math.pow((circleCoOrdinates[0] - selectedX), 2) + Math.pow((circleCoOrdinates[1]-selectedY), 2);
            boolean isInside = distance <= Math.pow(Constants.DEFAULT_DIAMETER, 2);
            if(isInside) {
                return entry.getKey().intValue();
            }
        }
        return -1;
    }

    public List<Integer[]> generateRandomCoOrdinatesForARound(int round) {
        GameData gameData = GameData.getInstance();
        // We have exclude this area from width so that neighborhoods won't go outside the field. Two is just a buffer
        int fieldWidthMin = (Utils.getNeighborhoodSizeInARound(round)/2) + 2;
        int fieldWidthMax = Constants.GAME_FIELD_WIDTH - (fieldWidthMin);
        // 20 is pixels we used to display Round Number. A neighborhood cannot stretch into that label
        int fieldHeightMin = (Utils.getNeighborhoodSizeInARound(round)/2) + 2 + 20;
        int fieldHeighMax = Constants.GAME_FIELD_HEIGHT - ((Utils.getNeighborhoodSizeInARound(round)/2) + 2);
        Random random = new Random();
        List<Integer[]> randomCoOrdinates = new ArrayList<>();
        for(int i=0; i<gameData.getDifficultyLevel(); i++) {
            boolean insideNeighborhood = true;
            int randomX = -1;
            int randomY = -1;
            while(insideNeighborhood) {
                randomX = random.nextInt((fieldWidthMax - fieldWidthMin) + 1) + fieldWidthMin;
                randomY = random.nextInt((fieldHeighMax - fieldHeightMin) + 1) + fieldHeightMin;
                insideNeighborhood = checkIfNewCoOrdinatesAreInNeighborhoodOfOtherCoOrdinates(randomX, randomY, randomCoOrdinates, round);
                System.out.println("Generated point is inside neighborhood. Generating again");
            }
            randomCoOrdinates.add(new Integer[] {randomX, randomY});
        }
        System.out.println("Co-ordinates generated for round: " + round + "::"+ randomCoOrdinates);
        return randomCoOrdinates;
    }

    public boolean checkIfNewCoOrdinatesAreInNeighborhoodOfOtherCoOrdinates(int x, int y, List<Integer[]> otherCoOrdinates, int currentRound) {

        for(Integer[] point : otherCoOrdinates) {
            System.out.println("Generated : " + x + " : " + y + " is compared against :" + point[0] + " : " + point[1]);
            double pointsDistance = Math.pow(point[0]-x, 2) + Math.pow(point[1]-y, 2);
            double expected = Math.pow(Utils.getNeighborhoodHypotenuse(currentRound), 2);
            System.out.println("Points distance " + pointsDistance);
            System.out.println("expected " + expected);
            boolean response =  pointsDistance<= expected;
            if(response) {
                return true;
            }
        }
        return false;
    }



    public void disableAllControls(Container comp) {
        changeAllControls(comp, false);
    }

    public void enableAllControls(Container comp) {
        changeAllControls(comp, true);
    }

    private void changeAllControls(Container comp, boolean enable) {
        for (Component childComp : comp.getComponents()) {
            if (childComp instanceof JButton) {
                ((JButton) childComp).setEnabled(enable);
            } else if (childComp instanceof JSlider) {
                JSlider slider = (JSlider) childComp;
                slider.setEnabled(enable);
            }
            else if(childComp instanceof Container){
                childComp.setEnabled(enable);
                changeAllControls((Container)childComp, enable);
            }
        }
    }

    public void gameCompleted() {
        GameData gameData = GameData.getInstance();
        gameData.setRound(0);
        gameData.getRefreshTimerForGivenRound().stop();
        gameData.setRefreshTimerForGivenRound(null);
        gameData.setBurstingForGivenRoundStarted(false);
        gameData.setMainCoOrdinatesForARound(new ArrayList<>());
        gameData.setGameStarted(false);
        gameData.setGameEnded(true);
        gameData.setGameTime(0);
        gameData.enableAllControls(GameControlUI.getPanel());
        gameData.setSelectionMessageShown(false);
    }
}

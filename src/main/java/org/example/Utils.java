package org.example;

import java.util.Random;

import static org.example.Constants.DEFAULT_NEIGHBORHOOD;

public class Utils {

    public static int getNeighborhoodSizeInARound(int currentRound) {
        return DEFAULT_NEIGHBORHOOD + (currentRound-1)*18;
    }

    public static double getNeighborhoodHypotenuse(int currentRound) {
        return Math.sqrt(2) * getNeighborhoodSizeInARound(currentRound);
    }

    public static int getTimeForCurrentRound(int currentRound) {
        return Constants.STARTING_TIME_IN_SECONDS - ((currentRound-1)*1);
    }

}

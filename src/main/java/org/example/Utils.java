package org.example;

import static org.example.Constants.DEFAULT_NEIGHBORHOOD;

public class Utils {

    public static int getNeighborhoodSizeInARound(int currentRound) {
        return DEFAULT_NEIGHBORHOOD + (currentRound-1)*18;
    }

    public static double getNeighborhoodHypotenuse(int currentRound) {
        return Math.sqrt(2) * getNeighborhoodSizeInARound(currentRound);
    }
}

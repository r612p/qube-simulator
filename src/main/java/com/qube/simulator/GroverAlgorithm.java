package com.qube.simulator;

import java.util.Random;

public class GroverAlgorithm {
    public static String simulate(int N, int target) {
        Random rand = new Random();
        int attempts = (int) Math.ceil(Math.sqrt(N));
        for (int i = 1; i <= attempts; i++) {
            int guess = rand.nextInt(N);
            if (guess == target) {
                return "Target found: " + target + " in " + i + " attempt(s)";
            }
        }
        return "Target not found. Try again.";
    }
}

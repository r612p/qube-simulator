package com.qube.simulator;

public class ShorAlgorithm {
    public static String simulate(int N) {
        for (int a = 2; a < N; a++) {
            int g = GCD.gcd(a, N);
            if (g > 1) return "Trivial factor found: " + g;

            int r = -1;
            int result = 1;
            for (int i = 1; i < N; i++) {
                result = (result * a) % N;
                if (result == 1) {
                    r = i;
                    break;
                }
            }

            if (r == -1 || r % 2 != 0) continue;

            int x = (int)Math.pow(a, r / 2) % N;
            if (x == 1 || x == N - 1) continue;

            int f1 = GCD.gcd(x - 1, N);
            int f2 = GCD.gcd(x + 1, N);

            if (f1 != 1 && f2 != 1) return "Factors of " + N + ": " + f1 + " and " + f2;
        }
        return "Failed to factor " + N;
    }
}

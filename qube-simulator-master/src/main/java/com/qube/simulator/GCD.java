 package com.qube.simulator;

public class GCD{
public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }           //used Ai for this we can modify it later I just need it temp for shors algo
}

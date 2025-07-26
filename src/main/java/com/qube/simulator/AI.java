
 package com.qube.simulator;
public class AI{

public static void main(String[] args){

    int count1 = 0;

for (int i = 0; i < 1000; i++) {
    Qubit q = new Qubit(); // Initializes to |0⟩

    q.HGate();
    q.ZGate();
    q.HGate();

    String result = q.executeSingle();

    if (result.equals("1")) count1++;
}

System.out.println("Measured 1: " + count1);


}       
}

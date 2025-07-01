 package com.qube.simulator;


import java.util.ArrayList;
public class AI{

public class GCD{
public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }           //used Ai for this we can modify it later I just need it temp for shors algo

    public static void main(String[] args) {
        int num1 = 48;
        int num2 = 18;

        int result = gcd(num1, num2);
        System.out.println("GCD of " + num1 + " and " + num2 + " is " + result);
    }
}





public static void main(String[] args){

        /*System.out.println("For the following, enter your complex numbers in the form of a + bi and c + di:");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a (real part of |0) amplitude): ");
        int a = input.nextInt();

        System.out.println("Enter b (imaginary part of |0) amplitude): ");
        int b = input.nextInt();

        System.out.println("Enter c (real part of |1) amplitude): ");
        int c = input.nextInt();

        System.out.println("Enter d (imaginary part of |1) amplitude): ");
        int d = input.nextInt();


*/
        Qubit qu = new Qubit();
        Qubit qu2 = new Qubit();
        Qubit qu3 = new Qubit();
        Qubit qu4 = new Qubit(1,2,3,4);
        Qubit qu5 = new Qubit(1,2,3,4);
        Qubit qu6 = new Qubit(1,2,3,4);

        Qubit qu7 = new Qubit();
        Qubit qu8 = new Qubit();

        MultiQubit mq1 = new MultiQubit(10);    //max is 25 on my pc

        System.out.println(mq1.executeMulti());
        
        


        System.out.println("Qubit Whole Probability: "+ (qu.getProbabilityA() + qu.getProbabilityB()));
        System.out.println("Qubit a Probability: "+ qu.getProbabilityA() + "\nQubit b Probability: "+ qu.getProbabilityB());
        System.out.println("One qubit identification: " + qu.execute());
        



        System.out.println("Before Gate:");
        System.out.println(qu.toString());
        qu.XGate();
        System.out.println("After XGate:");
        System.out.println(qu.toString());

        qu2.YGate();
        System.out.println("After YGate:");
        System.out.println(qu2.toString());

        qu3.ZGate();
        System.out.println("After ZGate:");
        System.out.println(qu3.toString());

        qu4.HGate();
        System.out.println("After HGate:");
        System.out.println(qu4.toString());

        qu5.TGate();
        System.out.println("After TGate:");
        System.out.println(qu5.toString());
        qu5.TDaggerGate();
        System.out.println("After TDaggerGate:");
        System.out.println(qu5.toString());

        qu6.SGate();
        System.out.println("After SGate:");
        System.out.println(qu6.toString());
        qu6.SDaggerGate();
        System.out.println("After SDaggerGate:");
        System.out.println(qu6.toString());




        //shors algo
        //FACTORING 15

        MultiQubit mqSHORIn = new MultiQubit(8);
        MultiQubit mqSHOROut = new MultiQubit(8);

       
         for(int i = 0; i < 8; i++){
                mqSHORIn.addGate("Hadamard", i, 0);
        }

        System.out.println(mqSHORIn.executeCircut());
        


//circut testing
System.out.println("begin circut testing");
System.out.println("");
System.out.println("");

MultiQubit circuit = new MultiQubit(3);

    // adding gates
    circuit.addGate("Hadamard", 0, 0);
    circuit.addGate("X", 1, 0);
    circuit.addGate("Z", 2, 0); 
    String output = circuit.executeCircut();
    System.out.println("Circuit Output: " + output);
    for (int i = 0; i < circuit.getNumQubits(); i++) {
        System.out.println("Qubit " + i + " state: " + circuit.getQubitFromMulti(i));
    }
}



        
}






class MultiQubit {

    public ArrayList<Qubit> entangled = new ArrayList<>();
    public ArrayList<String> entangledOutputs = new ArrayList<>();
    private int numQubits;
    public ArrayList<ArrayList<String>> workspace;

    public MultiQubit(int num) {
        numQubits = num;

        // Initialize qubits
        for (int i = 0; i < num; i++) {
            entangled.add(new Qubit());
        }

        // Generate all binary output strings
        for (int i = 0; i < (int) Math.pow(2, numQubits); i++) {
            String binary = Integer.toBinaryString(i);
            while (binary.length() < numQubits) {
                binary = "0" + binary;
            }
            entangledOutputs.add(binary);
        }


        workspace = new ArrayList<>();
        for (int i = 0; i < numQubits; i++) {
            workspace.add(new ArrayList<>());
        }
    }


   public String executeMulti() {
        StringBuilder answer = new StringBuilder();
        for (Qubit qz: entangled) {
            answer.append(qz.execute()).append(" ");
        }
        return answer.toString().trim();
    }






     public String executeCircut() {
    // finds max layers
    int maxLayers = 0;
    for (ArrayList<String> row : workspace) {
        maxLayers = Math.max(maxLayers, row.size());
    }

    // outter columns
    for (int c = 0; c < maxLayers; c++) {


        //inner
        for (int r = 0; r < workspace.size(); r++) {
            ArrayList<String> row = workspace.get(r);


            if (c >= row.size()) continue;

            String gate = row.get(c);
            if (gate == null) continue;

            if (gate.equals("X")) {
                getQubitFromMulti(r).XGate();
            }

            if (gate.equals("Y")) {
                getQubitFromMulti(r).YGate();
            }

            if (gate.equals("Z")) {
                getQubitFromMulti(r).ZGate();
            }

            if (gate.equals("S")) {
                getQubitFromMulti(r).SGate();
            }

            if (gate.equals("T")) {
                getQubitFromMulti(r).TGate();
            }

            if (gate.equals("SDagger")) {
                getQubitFromMulti(r).SDaggerGate();
            }

            if (gate.equals("TDagger")) {
                getQubitFromMulti(r).TDaggerGate();
            }

            if (gate.equals("Hadamard")) {              //gotta be a way to make this shorter lol instead of spamming if statements
                getQubitFromMulti(r).HGate();
            }
        }
    }

    return executeMulti();
}



public void addGate(String gate, int qube, int layer) {
        ArrayList<String> row = workspace.get(qube);
        while (row.size() <= layer) {
            row.add(null); 
        }
        row.set(layer, gate);
    }




public int getNumQubits(){
        return numQubits;
}

public Qubit getQubitFromMulti(int selection){
        return entangled.get(selection);
}

}








//complex number class start------------------------------------------------------------------------------------------------------

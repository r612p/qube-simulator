
 package com.qube.simulator;
public class AI{

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
        System.out.println("One qubit identification: " + qu.executeSingle());
        



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
 
Qubit q1 =new Qubit(1,0,0,0);
        Qubit q2 = new Qubit(1,0,0,0);
        ArrayList<Qubit> qubits = new ArrayList<>();
        qubits.add(q1);
        qubits.add(q2);
MultiQubit circuit = new MultiQubit(qubits);

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

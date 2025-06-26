import java.util.ArrayList;
public class AI{

 class GCD{
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


 static class Qubit{
     private complexNumber a;
     private complexNumber b;
     private boolean collapsed = false;
     private String measuredValue = null;


   public Qubit(){  //base constructor, represented by a∣0⟩+b∣1⟩, a=0, b=1

        a = new complexNumber(0, 0);                                                                                                               // by the way I changed it from 00 10 to 10 00 so it outputs 0
        b = new complexNumber(1, 0);

    }
        public Qubit(int ar, int ai, int br, int bi){  //constructor, only for specific examples where you want to create your own qubit. This is not possible irl.

        a = new complexNumber(ar, ai);
        b = new complexNumber(br, bi);

    }
    
    double[][] zeroREP = {
    {1},                        //how much of it is zero, ALSO THE BASE STATE
    {0}};                       //how much of it is one

    double[][] oneREP  = {
    {0},
    {1}};
       

    complexNumber[][] abt = {
    {a},
    {b}};

public double getProbabilityA(){
        return Math.pow(a.getReal(), 2) + Math.pow(a.getImaginary(), 2);
}

public double getProbabilityB(){
        return Math.pow(b.getReal(), 2) + Math.pow(b.getImaginary(), 2);
}

public complexNumber getA(){
        return a;
}

public complexNumber getB(){
        return b;
}

public String execute(){
        double num = Math.random();
    if (collapsed == true) {
        return measuredValue;
    }
    else{
         if(num < Math.pow(a.getReal(), 2) + Math.pow(a.getImaginary(), 2)){
                collapsed = true;
                return "0";
         }
        else{
                collapsed = true;
                return "1";
        }
}
}

public void resetCollapse(){
    collapsed = false;
    measuredValue = null;
}


public void XGate(){
        complexNumber temp = a;
        a = b;
        b = temp;
}

public void YGate(){                                                                            //there may be an issue since b real yields -0.0 when it should be 0.0
        complexNumber newA = new complexNumber(b.getImaginary(), -b.getReal());

    
        complexNumber newB = new complexNumber(-a.getImaginary(), a.getReal());

        a = newA;
        b = newB;
}

public void ZGate(){
        b.setReal(-b.getReal());
        b.setImaginary(-b.getImaginary());
}

public void HGate(){
        double rttwo=1.0/Math.sqrt(2);
        complexNumber newA = new complexNumber((a.getReal()+b.getReal())*rttwo, (a.getImaginary()+b.getImaginary())*rttwo);
        complexNumber newB = new complexNumber((a.getReal()-b.getReal())*rttwo, (a.getImaginary()-b.getImaginary())*rttwo);

        a = newA;
        b = newB;
}

public void SGate(){
        complexNumber newB = new complexNumber(b.getImaginary()*-1,b.getReal());
        
        b = newB;

}

/*public void TGate(){
        double r = 1.0 / Math.sqrt(2);
        
        double newReal = b.getReal() * r - b.getImaginary() * r;
        double newImag = b.getReal() * r + b.getImaginary() * r;
        b = new complexNumber(newReal, newImag);

}*/

public void TGate(){
        double r = 1.0 / Math.sqrt(2);
        complexNumber multiplier = new complexNumber(r,r);

        b.complexMultiply(multiplier);
        

}
public void SDaggerGate(){
       complexNumber newB = new complexNumber(b.getImaginary()*-1,b.getReal());
        
        b = newB; 

}

public void TDaggerGate(){
        double r = 1.0 / Math.sqrt(2);
        
        complexNumber multiplier = new complexNumber(r,-r);

        b.complexMultiply(multiplier);

}

public String toString(){
        String realA = "" + getA().getReal();
        String iA = "" + getA().getImaginary();
        String realB = "" + getB().getReal();
        String iB = "" + getB().getImaginary();


        return "(" + realA + " + " + iA + "i)" + "|0>" + " + (" + realB + " + " + iB + "i)" + "|1>";
}
}



 static class MultiQubit {

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
 static class complexNumber{
        public double real;
        public double imaginary;

        public complexNumber(double real, double imaginary){
                this.real = real;
                this.imaginary = imaginary;
        }


           public complexNumber(){
                real = 0;
                imaginary = 0;
        }

        public void complexAdd(double num){ //adds a real number to the complex number
                real += num;
        }

        public void complexAdd(complexNumber num){ //adds complex to complex
                real += num.getReal();
                imaginary += num.getImaginary();
        }

        public void complexMultiply(complexNumber num){
                double a = real;
                double b = imaginary;
                double c = num.getReal();
                double d = num.getImaginary();

                real = a * c - b * d;
                imaginary = a * d + b * c;
        }

//getters
        public double getReal(){
                return real;
        }

        public double getImaginary(){
                return imaginary;
        }

//setters
        public void setReal(double num){
                real = num;
        }

        public void setImaginary(double num){
                imaginary = num;
        }
}       

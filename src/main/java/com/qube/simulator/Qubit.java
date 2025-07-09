 package com.qube.simulator;




class Qubit{
     private complexNumber a;
     private complexNumber b;
     private boolean collapsed = false;
     private String measuredValue = null;




    public Qubit(){  //base constructor, represented by a∣0⟩+b∣1⟩, a=0, b=1


        a = new complexNumber(0, 0);                                                                                                               // by the way I changed it from 00 10 to 10 00 so it outputs 0
        b = new complexNumber(1, 0);


    }
        public Qubit(double ar, double ai, double br, double bi){  //constructor, only for specific examples where you want to create your own qubit. This is not possible irl.


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


public String executeSingle(){
        double num = Math.random();
   if (collapsed) {
    return measuredValue;
} else {
    double probA = Math.pow(a.getReal(), 2) + Math.pow(a.getImaginary(), 2);
    if (num < probA) {
        collapsed = true;
        measuredValue = "0";
    } else {
        collapsed = true;
        measuredValue = "1";
    }
    return measuredValue;
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


//setters, only use for MultiQubit!!


void setA(complexNumber newA) {
    a = new complexNumber(newA.getReal(), newA.getImaginary());
}


void setB(complexNumber newB) {
    b = new complexNumber(newB.getReal(), newB.getImaginary());
}














}




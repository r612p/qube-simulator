 package com.qube.simulator;

 
 
 
 class complexNumber{
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

package com.furkantektas.braingames.datatypes;

import com.furkantektas.braingames.R;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Sinan NAR on 21/11/14.
 */
public class MathOperation {

    public Operation mOperation;
    private int firstNumber;
    private int secondNumber;
    private int result;

    public MathOperation(Operation operation) {
        Random r = new Random();
        this.mOperation = operation;
        switch (mOperation){
            case ADDITION:
            case EXTRACTION:
            {
                do{
                    firstNumber = r.nextInt(100);
                    secondNumber = r.nextInt(10);
                }while(firstNumber == 0 || firstNumber == 1 || secondNumber == 0 || secondNumber == 1);

            }
            break;
            case MULTIPLICATION:
            {
                do{
                    firstNumber = r.nextInt(10);
                    secondNumber = r.nextInt(20);
                }while(firstNumber == 0 || firstNumber == 1 || secondNumber <4);

            }
            break;
            case DIVISION:
            {
                ArrayList<Integer> primeNumbers;
                do{
                    do{
                        firstNumber = r.nextInt(100);
                    }while(firstNumber<50);
                    primeNumbers = findPrimeFactors(firstNumber);
                    secondNumber = primeNumbers.get(primeNumbers.size()-1).intValue();
                }while(firstNumber == 0 || firstNumber == 1 || primeNumbers.size()<2);

            }
            break;
        }

        switch (mOperation){
            case ADDITION:{result = firstNumber + secondNumber;}break;
            case EXTRACTION:{result = firstNumber - secondNumber;}break;
            case MULTIPLICATION:{result = firstNumber * secondNumber;}break;
            case DIVISION:{result = firstNumber / secondNumber;}break;
        }

    }

        public MathOperation(Operation operation,int firstNumber,int secondNumber) {
        this.mOperation = operation;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        switch (mOperation){
            case ADDITION:{result = firstNumber + secondNumber;}break;
            case EXTRACTION:{result = firstNumber - secondNumber;}break;
            case MULTIPLICATION:{result = firstNumber * secondNumber;}break;
            case DIVISION:{result = firstNumber / secondNumber;}break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Operation))return false;
        MathOperation sOther = (MathOperation)o;
        return (sOther.mOperation.resId == mOperation.resId);
    }

    public boolean isOperation(Operation o){
        return mOperation.getResId() == o.getResId();
    }
    public boolean isResult(int r){return r==this.result;}


    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static enum Operation {
        ADDITION(R.string.addition),
        EXTRACTION(R.string.extraction),
        MULTIPLICATION(R.string.multiplication),
        DIVISION(R.string.division);

        private int resId;

        private Operation(int resId) {
            this.resId = resId;
        }

        public int getResId() {
            return resId;
        }
    }

    public ArrayList<Integer> generateResults(){
        ArrayList<Integer> results = new ArrayList<Integer>();

        Random r = new Random();
        int randomStart = r.nextInt(4);

        switch (mOperation){
            case ADDITION:
            case EXTRACTION:{
                for(int i=0;i<randomStart;++i){
                    results.add(new Integer(result-i));
                }
                results.add(new Integer(result));
                for(int i=randomStart+1;i<4;++i){
                    results.add(new Integer(result+i));
                }
            }break;
            case MULTIPLICATION:{
                for(int i=0;i<randomStart;++i){
                    results.add(new Integer(firstNumber*(secondNumber-i)));
                }
                results.add(new Integer(result));
                for(int i=randomStart+1;i<4;++i){
                    results.add(new Integer(firstNumber*(secondNumber+i)));
                }
            }break;
            case DIVISION:{
                for(int i=0;i<randomStart;++i){
                    results.add(new Integer(firstNumber/(secondNumber-i)));
                }
                results.add(new Integer(result));
                for(int i=randomStart+1;i<4;++i){
                    results.add(new Integer(firstNumber/(secondNumber+i)));
                }
            }break;
        }


        return results;
    }

    public static Operation generateOperation(int ind) {
        int i = 0;
        Operation[] operations = Operation.values();

        if(ind >= operations.length)
            ind = (ind + operations.length) % operations.length;

        return operations[ind];
    }


    private static ArrayList<Integer> findPrimeFactors(int number) {
        int n = number;
        ArrayList<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }

}

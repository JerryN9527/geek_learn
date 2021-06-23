package com.deceen.jvm;

public class Hello2 {
    private static int[] numbers={1,6,8};
    public static void main(String[] args) {
        MovingAverage ma =new MovingAverage();
        for (int number:numbers){
            ma.submit(number);
        }
        double avg = ma.getAvg();
        test1();
    }

    private static void test1(){
        int a= 1;
    }

}

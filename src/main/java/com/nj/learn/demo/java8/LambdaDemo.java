package com.nj.learn.demo.java8;

/**
 * 简单的lambda定义
 */
public class LambdaDemo {

    public static void main(String[] args) {
        //定义方法
        MathOperation add = (a, b) -> a+b;
        MathOperation sub = (a, b) -> a-b;
        MathOperation mul = (a, b) -> a*b;
        MathOperation div = (a, b) -> a/b;


        //实现
        LambdaDemo lambdaDemo = new LambdaDemo();
        System.out.println("lambda 加法："+lambdaDemo.operate(1,2,add));
        System.out.println("lambda 减法："+lambdaDemo.operate(1,2,sub));
        System.out.println("lambda 乘法："+lambdaDemo.operate(1,2,mul));
        System.out.println("lambda 除法："+lambdaDemo.operate(1,2,div));
    }




    /**
     * 定义接口：
     */
    interface MathOperation<T>{
        T oprate(int a,int b);
    }


    /**
     * 定义引入方法
     * @param a
     * @param b
     * @param mathOperation
     * @param <T>
     * @return
     */
    public <T>T operate(int a, int b, MathOperation<T> mathOperation){
        return mathOperation.oprate(a,b);
    }

}

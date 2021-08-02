package com.example.seventhexample;

public class Calculator {
    public int sum(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("by zero division, return -1!");
        }
        return -1;
    }
}

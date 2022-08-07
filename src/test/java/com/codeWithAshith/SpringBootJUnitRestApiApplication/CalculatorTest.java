package com.codeWithAshith.SpringBootJUnitRestApiApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    public void testMultiply() {
        Calculator calculator = new Calculator();
        assertEquals(20, calculator.multiply(4, 5));
    }

    @Test
    public void testMultiply_Fail() {
        Calculator calculator = new Calculator();
        assertEquals(25, calculator.multiply(4, 5));
    }

    @Test
    public void testDivide() {
        Calculator calculator = new Calculator();
        assertEquals(1, calculator.divide(4, 4));
    }

    @Test
    public void testDivide_Fail() {
        Calculator calculator = new Calculator();
        assertEquals(25, calculator.multiply(4, 0));
    }
}

package com.example.seventhexample;

import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorUnitTest {
    private Calculator calc;

    @Before
    public void beforeClass() throws Exception {
        calc = new Calculator();
    }

    @Test
    public void addition_isCorrect() {      // wrong answer!
        assertEquals(777, calc.sum(2, 3));
    }

    @Test
    public void division_isCorrect() {
        assertEquals(3, calc.divide(7,2));
        assertEquals(-1, calc.divide(7,0));
    }

    @After
    public void afterClass() throws Exception {
        calc = null;
    }
}
package com.acme.sandbox;

import static com.acme.sandbox.Functions.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.math.BigInteger;

public class FunctionsTest {

  @Test
  public void testFactorial() throws Exception {
    assertEquals(BigInteger.valueOf(1), factorial(1));
    assertEquals(BigInteger.valueOf(2), factorial(2));
    assertEquals(BigInteger.valueOf(6), factorial(3));
    assertEquals(BigInteger.valueOf(120), factorial(5));
    assertEquals(BigInteger.valueOf(720), factorial(6));
    assertEquals(
        new BigInteger("933262154439441526816992388562667004907159682643816214"
            + "6859296389521759999322991560894146397615651828625369792082722375"
            + "8251185210916864000000000000000000000000"),
        factorial(100));
  }

  @Test
  public void testFibonacci() throws Exception {
    long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377,};
    for (int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], fibonacci(i));
    }
  }

  @Test
  public void testBinaryToDecimal() throws Exception {
    assertEquals(1, binaryToDecimal("1"));
    assertEquals(2, binaryToDecimal("10"));
    assertEquals(3, binaryToDecimal("11"));
    assertEquals(4, binaryToDecimal("100"));
    assertEquals(8, binaryToDecimal("1000"));
    assertEquals(16, binaryToDecimal("10000"));
    assertEquals(32, binaryToDecimal("100000"));
    assertEquals(64, binaryToDecimal("1000000"));
    assertEquals(429, binaryToDecimal("110101101"));
  }

  @Test
  public void binaryToDecimalShouldRequireBinaryValues() throws Exception {
    try {
      binaryToDecimal("3");
      fail("Expected an error");
    } catch (IllegalArgumentException e) {
      // good.
    }

    try {
      binaryToDecimal("abc");
      fail("Expected an error");
    } catch (IllegalArgumentException e) {
      // good.
    }
  }
}

package com.acme.sandbox;

import java.math.BigInteger;

import com.google.common.base.Preconditions;

/** Repository of stray functions. */
public final class Functions {

  public static BigInteger factorial(long n) {
    return factorial(BigInteger.valueOf(n));
  }

  public static BigInteger factorial(BigInteger n) {
    return n.compareTo(BigInteger.ONE) >= 0
        ? n.multiply(factorial(n.subtract(BigInteger.ONE)))
        : BigInteger.ONE;
  }

  public static long fibonacci(long n) {
    if (n < 1) {
      return 0;
    }
    return n < 3 ? 1 : fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static long binaryToDecimal(String binary) {
    Preconditions.checkArgument(
        binary.matches("^[0-1]*$"),
        "String parameter must be a binary value.");
    long decimal = 0;
    int two = 1;
    for (int i = binary.length() - 1; i >= 0; i--) {
      decimal += Byte.parseByte(String.valueOf(binary.charAt(i))) * two;
      two *= 2;
    }
    return decimal;
  }

  private Functions() {}
}

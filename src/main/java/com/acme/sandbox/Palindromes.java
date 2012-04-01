package com.acme.sandbox;

public class Palindromes {

  public static void main(String[] args) throws Exception {
    System.out.println("working...");

    long start = System.currentTimeMillis();
    Numbers numbers = findLowestPalindrome(100, 1000);
    long elapsed = System.currentTimeMillis() - start;

    System.out.printf(
        " lowest even is %d%n lowest odd number is %d%n Calculated in %.3f minutes.",
        numbers.lowestEven,
        numbers.lowestOdd,
        elapsed / 1000.0 / 60.0);

    // output
    //    working...
    //     lowest even is 2010102
    //     lowest odd number is 1030301
    //     Calculated in 2.090 minutes.
  }

  private static Numbers findLowestPalindrome(int min, int max) {
    Numbers numbers = new Numbers();

    // TODO: Can this be optimized?
    for (int i = min; i < max; i++) {
      for (int j = min; j < max; j++) {
        for (int k = min; k < max; k++) {
          final int number = i * j * k;
          if (!isPalindrome(number)) continue;
          boolean isEven = number % 2 == 0;
          if (isEven && (numbers.lowestEven == 0 || number < numbers.lowestEven)) {
            numbers.lowestEven = number;
          }
          if (!isEven && (numbers.lowestOdd == 0 || number < numbers.lowestOdd)) {
            numbers.lowestOdd = number;
          }
        }
      }
    }

    return numbers;
  }

  static boolean isPalindrome(int number) {
    // TODO: There is a mathematic way of doing this.
    String string = Integer.toString(number);
    for (int i = 0, j = string.length() - 1; i <= j; i++, j--)
      if (string.charAt(i) != string.charAt(j))
        return false;
    return true;
  }
}

class Numbers {
  public int lowestEven;
  public int lowestOdd;
}


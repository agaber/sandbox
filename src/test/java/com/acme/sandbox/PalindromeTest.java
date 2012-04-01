package com.acme.sandbox;

import static org.junit.Assert.*;

import org.junit.Test;

public class PalindromeTest {

  @Test
  public void itShoulCheckIfPalindrome() throws Exception {
    assertTrue(Palindromes.isPalindrome(12321));
    assertTrue(Palindromes.isPalindrome(523131325));
    assertTrue(Palindromes.isPalindrome(44));
    assertTrue(Palindromes.isPalindrome(555));
    assertTrue(Palindromes.isPalindrome(1));
    assertTrue(Palindromes.isPalindrome(23866832));

    assertFalse(Palindromes.isPalindrome(43));
    assertFalse(Palindromes.isPalindrome(12345));
    assertFalse(Palindromes.isPalindrome(998));
    assertFalse(Palindromes.isPalindrome(12));
    assertFalse(Palindromes.isPalindrome(98));
  }

}

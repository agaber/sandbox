package com.acme.sandbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PigLatinTranslatorTest {

  @Test
  public void toPigLatin() throws Exception {
    assertEquals(
        "isthay isay ustjay a esttay.",
        PigLatinTranslator.toPigLatin("This is just a test."));
  }
}
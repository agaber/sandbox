package com.acme.sandbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LambdasTest {
  @Test
  public void getMessage() throws Exception {
    assertEquals("hi dude", Lambdas.getMessage("salutation", "dude"));
    assertEquals("you suck man", Lambdas.getMessage("mean", "man"));
  }
}

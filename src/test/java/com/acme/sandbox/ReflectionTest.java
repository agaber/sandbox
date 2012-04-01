package com.acme.sandbox;

import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.junit.Test;

public class ReflectionTest {

  @Test
  public void nameShouldIncludePackage() throws Exception {
    assertEquals("com.acme.sandbox.ReflectionTest", getClass().getName());
  }
}

@Target({ElementType.METHOD, ElementType.FIELD})
@interface SomeObject {

}

package com.udemy.ztf.interview;

import static com.google.common.truth.Truth.assertThat;
import static com.udemy.ztf.interview.Main.*;

import org.junit.Test;

public class MainTest {

  @Test
  public void testPass() throws Exception {
    assertThat(pass("a")).isEqualTo("a");
  }
}

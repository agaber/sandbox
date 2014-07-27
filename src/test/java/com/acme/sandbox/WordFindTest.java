package com.acme.sandbox;

import static org.junit.Assert.assertArrayEquals;
import static java.nio.file.Files.write;
import static java.util.Arrays.asList;

import com.google.jimfs.Configuration;
import com.google.jimfs.Jimfs;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.concurrent.Executors;

public class WordFindTest {
  WordFind wordFind;

  @Before
  public void beforeEach() throws Exception {
    Path path = Jimfs.newFileSystem(Configuration.unix()).getPath("/find.txt");
    write(path, asList(
        "a",
        "at",
        "bar",
        "bat",
        "can",
        "car",
        "Cat",
        "rat",
        "Tan",
        "tAp",
        "tar",
        "to",
        "zip"));
    wordFind = new WordFind(Executors.newSingleThreadExecutor(), path);
  }

  @Test
  public void shouldFindWords() throws Exception {
    final char[][] matrix = {
      {'c', 'c', 'a', 't', 't', 'a', 'r'},
      {'a', 'a', 'c', 'a', 'n', 'a', 't'},
      {'r', 'c', 'n', 't', 'b', 'y', 'p'},
    };
    String[] wds = {"a", "at", "bar", "can", "car", "cat", "tan", "tap", "tar"};
    assertArrayEquals(wds, wordFind.find(matrix));
    assertArrayEquals(wds, wordFind.findSequentially(matrix));
  }

  @Test
  public void shouldFindHorizontally() throws Exception {
    final char[][] matrix = {
      {'c', 'c', 'a', 't', 't', 'a', 'r'},
      {'a', 'b', 'a', 't', 'p', 'i', 'z'},
    };
    assertArrayEquals(
        new String[]{"a", "at", "bat", "cat", "tar", "zip"},
        wordFind.find(matrix));
  }

  @Test
  public void shouldFindVertically() throws Exception {
    final char[][] matrix = {
      {'c', 'b', 'x', 'p', 't'},
      {'a', 'a', 'x', 'i', 'a'},
      {'t', 't', 'x', 'z', 'r'},
    };
    assertArrayEquals(
        new String[]{"a", "at", "bat", "cat", "tar", "zip"},
        wordFind.find(matrix));
  }

  @Test
  public void shouldFindiagonallyLeftToRight() throws Exception {
    final char[][] matrix = {
      {'c', 'b', 't', 'r'},
      {'a', 'a', 'a', 'o'},
      {'z', 't', 'r', 'r'},
    };
    assertArrayEquals(
        new String[]{"a", "at", "bar", "bat", "car", "rat", "tar", "to"},
        wordFind.find(matrix));
  }

  @Test
  public void shouldFindDiagonallyRightToLeft() throws Exception {
    final char[][] matrix = {
      {'a', 't', 'b', 'c'},
      {'o', 'a', 'a', 'a'},
      {'r', 'r', 't', 'z'},
    };
    assertArrayEquals(
        new String[]{"a", "at", "bar", "bat", "car", "tar", "to"},
        wordFind.find(matrix));
  }
}

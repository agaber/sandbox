package com.acme.sandbox;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

// Good example of word search game:
// http://www.wordgames.com/daily-word-search.html
public class WordFind {
  private final Set<String> dictionary;
  private final ExecutorService executor;

  public WordFind(ExecutorService executor, Path path) throws IOException {
    dictionary = ImmutableSet.copyOf(readAllLines(path, UTF_8)
        .parallelStream()
        .map(String::toLowerCase)
        .collect(Collectors.toSet()));
    this.executor = executor;
  }

  public String[] find(final char[][] matrix) {
    // TODO: Assert that all rows are the same size.

    if (matrix == null || matrix.length < 1) {
      return new String[0];
    }

    Future<Set<String>> horizontal = executor.submit(() -> {
      Set<String> words = new HashSet<>();
      for (char[] line : matrix) {
        words.addAll(findHorizontally(line));
      }
      return words;
    });

    Future<Set<String>> vertical = executor.submit(() -> {
      Set<String> words = new HashSet<>();
      for (int column = 0; column < matrix[0].length; column++) {
        words.addAll(findVertically(matrix, column));
      }
      return words;
    });

    // Look for words diagonally. We'll do this in the current thread.
    Set<String> words = new HashSet<>();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        words.addAll(findDiagonally(matrix, i, j));
      }
    }

    // TODO: Filter out overlapping words (take the biggest word).

    try {
      words.addAll(horizontal.get());
      words.addAll(vertical.get());
      return words.parallelStream().sorted().toArray(String[]::new);
    } catch (ExecutionException | InterruptedException e) {
      throw Throwables.propagate(e);
    }
  }

  public String[] findSequentially(final char[][] matrix) {
    Set<String> words = new HashSet<>();
    for (int row = 0; row < matrix.length; row++) {
      words.addAll(findHorizontally(matrix[row]));
      for (int column = 0; column < matrix[row].length; column++) {
        words.addAll(findVertically(matrix, column));
        words.addAll(findDiagonally(matrix, row, column));
      }
    }
    return words.parallelStream().sorted().toArray(String[]::new);
  }

  private Set<String> findDiagonally(char[][] matrix, int row, int column) {
    Set<String> words = new HashSet<>();
    String word = "", rtlWord = "";
    StringBuilder reverse = new StringBuilder();
    StringBuilder rtlReverse = new StringBuilder();
    for (int i = row, j = column;
         i < matrix.length && j < matrix[0].length;
         i++, j++) {
      char letter = matrix[i][j];
      collect(words, (word += letter), reverse.insert(0, letter));

      // Collect the words that go right to left diagonally.
      int reverseJ = matrix[0].length - 1 - j;
      char rtlLetter = matrix[i][reverseJ];
      collect(words, rtlWord += rtlLetter, rtlReverse.insert(0, rtlLetter));
    }
    return words;
  }

  private Set<String> findHorizontally(char[] line) {
    Set<String> words = new HashSet<>();
    for (int i = 0; i < line.length; i++) {
      String word = "";
      StringBuilder reverse = new StringBuilder();
      for (int j = i; j < line.length; j++) {
        collect(words, word += line[j], reverse.insert(0, line[j]));
      }
    }
    return words;
  }

  private Set<String> findVertically(char[][] matrix, int column) {
    Set<String> words = new HashSet<>();
    for (int i = 0; i < matrix.length; i++) {
      String word = "";
      StringBuilder reverse = new StringBuilder();
      for (int j = i; j < matrix.length; j++) {
        char letter = matrix[j][column];
        collect(words, word += letter, reverse.insert(0, letter));
      }
    }
    return words;
  }

  private void collect(Set<String> words, String word, StringBuilder reverse) {
    // We assume word and reverse are the same size.
    if (dictionary.contains(word)) {
      words.add(word);
    } else {
      String reverseString = reverse.toString();
      if (dictionary.contains(reverseString)) {
        words.add(reverseString);
      }
    }
  }
}

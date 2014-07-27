package com.acme.sandbox;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PigLatinTranslator {

  public static String toPigLatin(String text) {
    Objects.requireNonNull(text, "text can't be null");
    return words(text)
        .stream()
        .map(String::toLowerCase)
        .map(PigLatinTranslator::wordToPigLatin)
        .collect(Collectors.joining(" "))
        .replaceAll(" \\.", ".");
  }

  private static List<String> words(String text) {
    return asList(text.split(" |((?<=(\\.))|(?=(\\.)))"));
  }

  private static String wordToPigLatin(String word) {
    if (startsWithVowel(word)) {
      return word.equals("a") ? word : word + "ay";
    }
    String[] split = word.split("(?=(a|e|i|o|u))", 2);
    return split.length == 2 ? split[1] + split[0] + "ay" : word;
  }

  private static boolean startsWithVowel(String word) {
    return String.valueOf(word.charAt(0)).matches("a|e|i|o|u");
  }
}
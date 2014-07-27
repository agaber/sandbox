package com.acme.sandbox;

import static java.lang.System.out;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Tooling around java 1.8.
 *
 * <pre>
 * mvn exec:java -Dexec.mainClass="com.acme.sandbox.Lambdas"
 * </pre>
 */
class Lambdas {
  public static void main(String... args) throws Exception {
    final Path input  = Paths.get("/usr/share/dict/words");
    final Path output = Paths.get(System.getProperty("user.home"), "Desktop/find.txt");

    // Do each test in a separate thread for no good reason.
    ExecutorService executor = Executors.newCachedThreadPool();

    // Read system word dictionary to array.
    List<String> words = Files.readAllLines(input, StandardCharsets.UTF_8);

    // Do some weird manipulations on the find sequentially.
    Future<Long> sequential = executor.submit(() -> {
      long start = System.currentTimeMillis();
      manipulate(words, false);
      return System.currentTimeMillis() - start;
    });

    // Do some weird manipulations on the find in parallel.
    Future<Long> parallel = executor.submit(() -> {
      long start = System.currentTimeMillis();
      manipulate(words, true);
      return System.currentTimeMillis() - start;
    });

    // Display the results.
    out.printf(
        "Sequential manipulation completed in %d milliseconds.%n",
        sequential.get());
    out.printf(
        "Parallel manipulation completed in %d milliseconds.%n",
        parallel.get());

    // Write results for some reason.
    Files.write(output, manipulate(words, true), StandardCharsets.UTF_8);
    out.printf("Manipulated find written to %s.%n", output);

    // Let's count the characters in the unix word dictionary!
    long characters = words
        .parallelStream()
        .map(s -> s.length())
        .reduce(0, (c1, c2) -> c1 + c2);
    out.printf(
        "There are %s characters in the system word dictionary. Cool, right?%n",
        NumberFormat.getInstance().format(characters));

    executor.shutdown();
  }

  /** Does some weird shit to the collection param for the hell of it. */
  static List<String> manipulate(List<String> words, boolean parallel) {
    return (parallel ? words.stream() : words.parallelStream())
        .sorted((o1, o2) -> new Integer(o1.length()).compareTo(o2.length()))
        .map(s -> s.toLowerCase())
        .distinct()
        .filter(s -> s.length() > 10 && !s.contains("'"))
        .collect(Collectors.toList());
  }
}

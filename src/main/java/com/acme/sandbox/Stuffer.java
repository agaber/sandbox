package com.acme.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Code I saved from when I was looking for work back in 2008.
 */
public class Stuffer {

    public static void main(String[] args) {
        GenericSorter.runSort();

        System.out.printf("%n----------------%n");
        System.out.printf("Aegis Logic Test%n%n");
        String input = "xxx xyx xxx xyx xyx zzxyxxabxxx";
        System.out.printf(
            "input: %s%noutput: %s%n",
            input,
            Stuffer.characterCount(input.replaceAll(" ", "")));

        System.out.printf("%n----------------%n");
        System.out.printf("Fibonacci Recursive Test%n%n");
        for (int i = 0; i < 25; i++)
            System.out.print(fibonacci(i) + " ");

        System.out.printf("%n----------------%n");
        System.out.printf("Fibonacci Iterative Test%n%n");
        for (int i = 0; i < 25; i++)
            System.out.print(fibonacciIteratively(i) + " ");

        System.out.printf("%n----------------%n");
        System.out.printf("Power Recursive Test%n%n");
        int n = 7, m = 2;
        System.out.printf("%d ^ %d = %d", n, m, power(n, m));

        System.out.printf("%n----------------%n");
        System.out.printf("Power Iterative Test%n%n");
        n = 7; m = 2;
        System.out.printf("%d ^ %d = %d", n, m, powerIteratively(n, m));

        System.out.printf("%n----------------%n");
        System.out.printf("Project Euler Multiples Sum Test%n%n");
        int number = 1000, m1 = 3, m2 = 5;
        System.out.printf(
            "The sum of all numbers below %d that are multiples of %d and %d is %d",
            number,
            m1,
            m2,
            ProjectEuler.addNumbers(number, m1, m2));

        System.out.println();

        if (System.console() != null) {
          do {
              System.out.printf("%ntype some text and press enter: ");
              String value = System.console().readLine();
              System.out.printf(
                  "%s is %s palindrome%n",
                  value,
                  ProjectEuler.isPalindrome(value)
                      ? "a"
                      : "not a");
              System.out.print("Again? (y/n) ");
          } while (System.console().readLine().equals("y"));
        }

        System.out.printf("%n----------------%n");
        System.out.printf("DoubleClick Java Recursive Question%n%n");
        System.out.printf("calculate(%d) = %d", 5, DoubleClick.calculate(5));
    }//method

    /**
     * In mathematics, the Fibonacci numbers are a sequence of numbers named
     * after Leonardo of Pisa, known as Fibonacci, whose Liber Abaci published
     * in 1202 introduced the sequence to Western European mathematics.
     *
     * The sequence is defined by the following recurrence relation:
     * F(n):= {0 if n = 0;
     *            1 if n = 1;
     *            F(n-1) + F(n-2) if n > 1; }
     *
     * That is, after two starting values, each number is the sum of the two
     * preceding numbers. The first Fibonacci numbers (sequence A000045 in
     * OEIS), also denoted as Fn, for n = 0, 1, … , are:
     *
     * 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597,
     * 2584, 4181, 6765, 10946, ...
     *
     * The sequence named after Fibonacci was first described in Indian
     * mathematics.
     */
    public static int fibonacci(int n) {
        return n <= 1 ? n : fibonacci(n - 1) + fibonacci(n - 2);
    }//method

    /** same thing iteratively (this was actually harder for me */
    public static int fibonacciIteratively(int n) {
        if (n <= 1)
            return n;

        int number = 0;
        int previous = 1;

        for (int i = 0; i < n; i++) {
            int temp = number;
            number += previous;
            previous = temp;
        }

        return number;
    }//method

    public static long power(int n, int m) {
        if (m == 1)
            return n;

        if (m == 0)
            return 0;

        return n * power(n, m - 1);
    }//method

    public static long powerIteratively(int n, int m) {
        if (m == 0)
            return 0;

        int power = 1;
        for (int i = 0; i < m; i++) {
            power *= n;
        }

        return power;
    }

    /**
     * Aegis Logic Test.
     *
     * Program, in a standard programming language (e.g. Java, C++, etc.), a fast and simple
     * solution to the following problem without using any regular expression utilities. Given a
     * stream of characters, output an "A" if the characters "xxx" are found in exactly that
     * sequence. If the characters "xyx" are found instead, output a "B". Do not re-process
     * characters so as to output both an "A" and a "B" when processing the same input.
     */
    public static String characterCount(String input) {
        //build a lookup table
        Map<String, String> knownSequences = new HashMap<String, String>();
        knownSequences.put("xxx", "A");
        knownSequences.put("xyx", "B");

        //some method variables
        char[] code = input.toCharArray();
        StringBuilder temp = new StringBuilder();
        StringBuilder output = new StringBuilder();

        for(char letter : code) {
            temp.append(letter);
            String knownSequence = knownSequences.get(temp.toString());

            //store the value from the lookup table in the output if this is a known sequence and start over
            if (knownSequence != null) {
                output.append(knownSequence);
                temp = new StringBuilder();
            }
        }

        //return the found letters
        return output.toString();
    }//method

}//class

/**
 * Simple Sort Algorithm tests that also use Generics.
 */
class GenericSorter {

    /** Sort using Bubble Sort */
    public static <T extends Comparable<T>> void bubbleSort(List<T> unsorted) {
        for (int i = unsorted.size() - 1; i > 0; i--)
            for (int j = 0; j < i; j++)
                if (unsorted.get(j).compareTo(unsorted.get(j + 1)) > 0)
                    swap(unsorted, j, j + 1);
    }//method

    /** swap method required by some sort algorithms */
    private static <T> void swap(List<T> list, int i, int j) {
        T temp = list.get(j);
        list.set(i, list.get(j));
        list.set(j, temp);
    }//method

    /** was previously in a main method somewhere */
    public static void runSort() {
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++)
            list.add(random.nextInt(100));

        GenericSorter.bubbleSort(list);
        for (int i : list)
            System.out.print(i + " ");
    }//method

}//class

/**
 * <a href="http://projecteuler.net/index.php?section=problems">Project Euler Problems</a>
 */
final class ProjectEuler {

    /** add all numbers below 1000 that are multiples of 3 and 5
     *  -- changed to be slightly generic.
     * @param n sum will be calculated from numbers below this value
     * @param multiples array of multiples to be considered
     * @return the sum of all numbers below n that are multiples of the values
     *    in the second parameter
     */
    public static int addNumbers(final int n, final int... multiples) {
        int sum = 0;

        for (int i = 1; i < n; i++)
            if (isMultiple(i, multiples))
                sum += i;

        return sum;
    }//method

    private static boolean isMultiple(int n, int... multiples) {
        for (int multiple : multiples)
            if (n % multiple == 0)
                return true;
        return false;
    }//method

    public static boolean isPalindrome(String value) {
        char[] letters = value.toCharArray();

        for (int i = 0; i < letters.length / 2; i++)
            if (letters[i] != letters[letters.length - i - 1])
                return false;

        return true;
    }//method

}//class

final class DoubleClick {

    public static int calculate(int n) {
        if (n <= 1)
            return 1;

        //System.out.printf("calculate(%1$d - 1) + %1$d%n", n);
        return calculate(n - 1) + n;
    }
}
import inspect, os, sys
path = os.path.dirname(inspect.getfile(inspect.currentframe()))
path = os.path.abspath(path + '/..')
path = path.replace('test', 'main')
sys.path.append(path)

from acme.algorithms import *
import unittest

class AlgorithmsTest(unittest.TestCase):

  def test_fibonacci_recursive(self):
    # 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144
    self.assertEqual(0, fibonacci_recursive(0))
    self.assertEqual(1, fibonacci_recursive(1))
    self.assertEqual(1, fibonacci_recursive(2))
    self.assertEqual(2, fibonacci_recursive(3))
    self.assertEqual(3, fibonacci_recursive(4))
    self.assertEqual(5, fibonacci_recursive(5))
    expected = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144]
    for i in range(0, len(expected)):
      self.assertEqual(expected[i], fibonacci_recursive(i))

  def test_fibonacci_iterative(self):
    # 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144
    self.assertEqual(0, fibonacci_iterative(0))
    self.assertEqual(1, fibonacci_iterative(1))
    self.assertEqual(1, fibonacci_iterative(2))
    self.assertEqual(2, fibonacci_iterative(3))
    self.assertEqual(3, fibonacci_iterative(4))
    self.assertEqual(5, fibonacci_iterative(5))
    expected = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144]
    for i in range(0, len(expected)):
      self.assertEqual(expected[i], fibonacci_iterative(i))

  def test_fibonacci(self):
    # 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144
    self.assertEqual(0, fibonacci(0))
    self.assertEqual(1, fibonacci(1))
    self.assertEqual(1, fibonacci(2))
    self.assertEqual(2, fibonacci(3))
    self.assertEqual(3, fibonacci(4))
    self.assertEqual(5, fibonacci(5))
    expected = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144]
    for i in range(0, len(expected)):
      self.assertEqual(expected[i], fibonacci(i))

  def test_fibonacci_biginput(self):
    # Recusive solution on large  input without memoization takes too long.
    # self.assertEqual(12586269025, fibonacci_recursive(50))
    self.assertEqual(12586269025, fibonacci_iterative(50))
    self.assertEqual(12586269025, fibonacci(50))


  def test_power(self):
    self.assertEqual(pow(1, 2), power(1, 2))
    self.assertEqual(pow(2, 2), power(2, 2))
    self.assertEqual(pow(3, 5), power(3, 5))
    self.assertEqual(pow(4, 0), power(4, 0))
    self.assertEqual(pow(5, 1), power(5, 1))
    self.assertEqual(pow(6, 20), power(6, 20))
    self.assertEqual(pow(7, 3), power(7, 3))

  def test_bubble_sort(self):
    unsorted = [10, 8, 6, 1, 2, 4, 9, 3, 7, 5]
    expected = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    self.assertEqual(expected, bubble_sort(unsorted))

  def test_selection_sort(self):
    unsorted = [10, 8, 6, 1, 2, 4, 9, 3, 7, 5]
    expected = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    # TODO
    # self.assertEqual(expected, selection_sort(unsorted))

  def test_merge_sort(self):
    unsorted = [10, 8, 6, 1, 2, 4, 9, 3, 7, 5]
    expected = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    # TODO
    # self.assertEqual(expected, merge_sort(unsorted))

  def test_quick_sort(self):
    unsorted = [10, 8, 6, 1, 2, 4, 9, 3, 7, 5]
    expected = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    # TODO
    # self.assertEqual(expected, quick_sort(unsorted))

  def test_max_pairwise_product(self):
    self.assertEqual(6, max_pairwise_product([1, 2,3]))
    self.assertEqual(90, max_pairwise_product([3, 1, 2, 10, 9, 7, 8, 4, 5, 6]))
    self.assertEqual(1, max_pairwise_product([1]))
    self.assertEqual(0, max_pairwise_product([]))

  def test_gcd(self):
    self.assertEqual(2, gcd(4, 10))
    self.assertEqual(2, gcd(10, 4))
    self.assertEqual(3, gcd(357, 234))
    self.assertEqual(61232, gcd(3918848, 1653264))

if __name__ == '__main__':
  unittest.main()

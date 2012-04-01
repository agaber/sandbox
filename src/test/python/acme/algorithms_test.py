import inspect, os, sys
path = os.path.dirname(inspect.getfile(inspect.currentframe()))
path = os.path.abspath(path + '/..')
path = path.replace('test', 'main')
sys.path.append(path)

from acme.algorithms import *
import unittest

class AlgorithmsTest(unittest.TestCase):

  def test_fibonacci(self):
    # 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144
    expected = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144]
    for i in range(0, len(expected)):
      self.assertEqual(expected[i], fibonacci(i))

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


if __name__ == '__main__':
  unittest.main()

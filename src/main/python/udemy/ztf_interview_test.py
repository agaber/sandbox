from ztf_interview import *
import unittest

class GoogleInterviewTest(unittest.TestCase):
  def setUp(self):
    self.goog = GoogleInterview()

  def test_has_pair_with_sum_naive(self):
    self.assertEqual(False, self.goog.has_pair_with_sum_naive([1, 2, 3, 9], 8))
    self.assertEqual(True, self.goog.has_pair_with_sum_naive([1, 2, 4, 4], 8))

  def has_pair_with_sum_sorted(self):
    self.assertEqual(False, self.goog.has_pair_with_sum_sorted([1, 2, 3, 9], 8))
    self.assertEqual(True, self.goog.has_pair_with_sum_sorted([1, 2, 4, 4], 8))

  def has_pair_with_sum_sorted(self):
    self.assertEqual(False, self.goog.has_pair_with_sum_unsorted([1, 2, 3, 9], 8))
    self.assertEqual(True, self.goog.has_pair_with_sum_unsorted([1, 2, 4, 4], 8))
    self.assertEqual(False, self.goog.has_pair_with_sum_unsorted([9, 2, 1, 3], 8))
    self.assertEqual(True, self.goog.has_pair_with_sum_unsorted([4, 2, 4, 1], 8))

if __name__ == '__main__':
  unittest.main()

"""Implementations of various algorithms."""

# This solution is slow because it computes the same result many times.
# Performs O(2^n).
def fibonacci_recursive(n: int) -> int:
  """Returns the nth number of the fibonacci sequence using a recursivion."""
  if n == 0:
    return 0
  elif n == 1:
    return 1
  else:
    return fibonacci_recursive(n - 1) + fibonacci_recursive(n - 2)

# Faster than recursive because it stores the previous calculation and reuses it.
# This uses memoization (stores past duplicative computations in memory).
def fibonacci_iterative(n: int) -> int:
  """Returns the nth number of the fibonacci sequence more efficiently."""
  assert n >= 0, "fibonacci can only be calculated for postitive integers"
  if n == 0:
    return 0
  results = [0, 1]
  for i in range(2, n + 1):
    results.append(results[i - 1] + results[i - 2])
  return results[n]

# Uses the recursive solution but avoids duplicative recalculations by storing
# past recusive calculations in a dictionary (the second optional param).
# Note: I don't like exposing memo in the public API and in a real world scenario
# I'd create a private version for the memo param.
def fibonacci(n, memo = {}):
  """Returns the nth number of the fibonacci sequence using an optimized 
  recursive solution."""
  if n in memo.keys():
    return memo[n]
  if n == 0:
    return 0
  if n == 1:
    return 1
  memo[n] = fibonacci(n - 1, memo) + fibonacci(n - 2, memo)
  return memo[n]

def power(n, m):
  """Compute n raised to the power of m recursively."""
  if m == 1:
    return n
  if m == 0:
    return 1
  return n * power(n, m - 1)

def bubble_sort(lst):
  """Sort a list using the bubble sort algorithm."""
  for i in range(len(lst) - 1, 0, -1):
    for j in range(i):
      if lst[j] > lst[j + 1]:
        # Swap (fancy python way)
        lst[j], lst[j + 1] = lst[j + 1], lst[j]
  return lst

def selection_sort(lst):
  """Sort a list using the selection sort algorithm."""
  for i in range(len(lst)):
    for j in range(len(lst) - 1):
      if lst[j + 1] < lst[j]:
        # Swap (not fancy old school way)
        temp = lst[j]
        lst[j] = lst[j + 1]
        lst[j + 1] = temp
  return lst

def insertion_sort(lst):
  pass

# Performs in n log(n).
# I don't like that this creates new lists.
def merge_sort(lst):
  """Sort a list using the merge sort algorithm."""
  if len(lst) == 1:
    return lst
  midpont = int(len(lst) / 2)
  left = lst[:midpont]
  right = lst[midpont:]
  return _merge(merge_sort(left), merge_sort(right))


def _merge(left, right):
  merged_list = []
  i = 0
  j = 0
  while i < len(left) and j < len(right):
    if left[i] < right[j]:
      merged_list.append(left[i])
      i += 1
    else:
      merged_list.append(right[j])
      j += 1
  while i < len(left):
    merged_list.append(left[i])
    i += 1
  while j < len(right):
    merged_list.append(right[j])
    j += 1
  return merged_list

def quick_sort(lst):
  """Sort a list using the quick sort algorithm."""
  pass

def heapsort(lst): 
  """Sort a list using the heapj sort algorithm."""
  pass

# From Coursera's Algorithmic Toolbox: Week 2, Exercise 2
def max_pairwise_product(numbers):
  n = len(numbers)
  if (n == 0):
    return 0;
  if n == 1:
    return numbers[0]
  sorted_numbers = sorted(numbers)
  return int(sorted_numbers[-1]) * int(sorted_numbers[-2])

def gcd(a: int, b: int) -> int:
  if b == 0:
    return a
  return gcd(b, a % b)

class MergeSortedArray:
  # Merge two sorted arrays so that the output is still sorted.
  # Example: [0, 3, 4, 31], [4, 6, 30] --> [0, 3, 4, 4, 6, 30, 31]
  # Note to self: Python doesn't really have arrays, just lists but whatever.
  def merge_sorted_arrays(self, arr1, arr2):
    merged = []
    arr1_index = 0
    arr2_index = 0
    while arr1_index < len(arr1) or arr2_index < len(arr2):
      arr1_val = arr1[arr1_index] if arr1_index < len(arr1) else None
      arr2_val = arr2[arr2_index] if arr2_index < len(arr2) else None
      if arr1_val is None and arr2_val is not None:
        merged.append(arr2_val)
        arr2_index += 1
      elif arr2_val is None and arr1_val is not None:
        merged.append(arr1_val)
        arr1_index += 1
      elif arr1_val < arr2_val:
        merged.append(arr1_val)
        arr1_index += 1
      elif arr1_val > arr2_val:
        merged.append(arr2_val)
        arr2_index += 1
      else:
        merged.append(arr2_val)
        arr2_index += 1
    return merged

  # Probably how I would do this problem in real life.
  # arr1.copy() probably runs in O(n) time making this solution O(n log n) and thus
  # worse than the previous solution. Firstly, we can try to avoid the copy, which
  # is easy if we don't care about mutating the parameters or a slightly less
  # efficient but much much more readable solution might be perfectly fine or even
  # preferred on reasonably sized inputs.
  def merge_sorted_arrays_simple(self, arr1, arr2):
    result = arr1.copy()
    result.extend(arr2)
    result.sort()
    return result


class TwoSum:
  """ From https://youtu.be/XKu_SEDAykw
  Similar question on leetcode: https://leetcode.com/problems/two-sum/
  We want to know if there is one pair (two numbers) that have a sum that equals
  the specified target sum.
  Example: [1, 2, 3, 9] Sum = 8 --> output No (False)
  Example: [1, 2, 4, 4] Sum = 8 --> output Yes (True) because 4 + 4 = 8
  """

  # Solution 1.
  # Assume sorted list.
  # Performs in O(n^2) quadratic time. Not good.
  def has_pair_with_sum_naive(self, arr: list[int], target_sum: int) -> bool:
    for i in range(len(arr)):
      for j in range(i + 1, len(arr)):
        current_sum = arr[i] + arr[j] 
        if current_sum == target_sum:
          return True
        if current_sum > target_sum:
          return False
    return False

  # Solution 2.
  # A less naive but still suboptimal solution is to use binary search for each
  # element in the array until a compliment (meaning arr[i] - target_sum) is
  # found. This performs in n log(n) time.

  # Solution 3.
  # A better solution: compare first and last elements and then move inward.
  # Performs in O(n) not bad!
  def has_pair_with_sum_sorted(self, arr: list[int], target_sum: int) -> bool:
    i = 0
    j = len(arr) - 1
    while i < j:
      sum = arr[i] + arr[j]
      if sum == target_sum:
        return True
      if sum > target_sum:
        j = j - 1
      else:
        i = i + 1
    return False

  # Solution 4.
  # But what if the array isn't sorted?
  # Example: [1, 3, 9, 2] Sum = 8 --> output No (False)
  # Example: [2, 4, 4, 1] Sum = 8 --> output Yes (True) because 4 + 4 = 8
  def has_pair_with_sum_unsorted(self, arr: list[int], target_sum: int) -> bool:
    memo = set()
    for i in range(len(arr)):
      if i in memo:
        return True
      memo.append(abs(arr[i] - target_sum))
    return 

  # Solution 5.
  # The last part of that interview was what to do if the input array doesn't fit
  # into memory. This seems like an obvious map-reduce teaser. The interviewee in
  # the video actually glosses over this and I suspect even in a real life
  # interview, at least for an L4 maybe L5, just knowing that MapReduce is an
  # option for really big data sets is good enough but there may actually be a
  # fair amount to consider when doing this with a distributed solution.

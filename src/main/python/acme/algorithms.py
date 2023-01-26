"""Implementations of various algorithms."""

# From Coursera's Algorithmic Toolbox: Week 2
# This solution is slow because it computes the same result many times.
def fibonacci_recursive(n: int) -> int:
  """Return the nth number of the fibonacci sequence using a recursivion."""
  if n == 0:
    return 0
  elif n == 1:
    return 1
  else:
    return fibonacci_recursive(n - 1) + fibonacci_recursive(n - 2)

# From Coursera's Algorithmic Toolbox: Week 2
# Faster than recursive because it stores the previous calculation and reuses it.
def fibonacci_iterative(n: int) -> int:
  """Return the nth number of the fibonacci sequence more efficiently."""
  assert n >= 0, "fibonacci can only be calculated for postitive integers"
  if n == 0:
    return 0
  results = [0, 1]
  for i in range(2, n + 1):
    results.append(results[i - 1] + results[i - 2])
  return results[n]

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
        lst[j], lst[j + 1] = lst[j + 1], lst[j]
  return lst

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

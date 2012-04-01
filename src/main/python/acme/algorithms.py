"""Implementations of various algorithms."""

def fibonacci(n):
  """Return the nth number of the fibonacci sequence."""
  if n == 0:
    return 0
  elif n == 1:
    return 1
  else:
    return fibonacci(n - 1) + fibonacci(n - 2)

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


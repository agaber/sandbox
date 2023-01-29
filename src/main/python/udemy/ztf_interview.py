if __name__ == '__main__':
  print('hello world')


class GoogleInterview:
  """ From https://youtu.be/XKu_SEDAykw
  We want to know if there is one pair (two numbers) that have a sum that equals
  the specified target sum.
  Example: [1, 2, 3, 9] Sum = 8 --> output No (False)
  Example: [1, 2, 4, 4] Sum = 8 --> output Yes (True) because 4 + 4 = 8

  Note: I've created a class so I can group this stuff together and hopefully
  keep it separated from other crap I add later.
  """

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

  # A less naive but still suboptimal solution is to use binary search for each
  # element in the array until a compliment (meaning arr[i] - target_sum) is found.
  # This performs in n log(n) time.

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

  # But what if the array isn't sorted?
  # Example: [1, 3, 9, 2] Sum = 8 --> output No (False)
  # Example: [2, 4, 4, 1] Sum = 8 --> output Yes (True) because 4 + 4 = 8

  def has_pair_with_sum_unsorted(self, arr: list[int], target_sum: int) -> bool:
    memo = set()
    for i in range(len(arr)):
      if i in memo:
        return True
      memo.append(abs(arr[i] - target_sum))
    return False
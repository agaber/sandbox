import inspect, os, sys
path = os.path.dirname(inspect.getfile(inspect.currentframe()))
path = os.path.abspath(path + '/..')
path = path.replace('test', 'main')
sys.path.append(path)

from acme.datastructures import *
import unittest

class DataStructuresTest(unittest.TestCase):

  def test_stack(self):
    stack = Stack()
    self.assertTrue(stack.is_empty())

    for i in range(1, 11):
      stack.push(i)
      self.assertFalse(stack.is_empty())

    for i in range(10, 0, -1):
      self.assertFalse(stack.is_empty())
      self.assertEqual(i, stack.peek())
      self.assertEqual(i, stack.pop())

    self.assertTrue(stack.is_empty())

  def test_queue(self):
    queue = Queue()
    self.assertTrue(queue.is_empty())

    for i in range(1, 11):
      queue.insert(i)
      self.assertFalse(queue.is_empty())

    for i in range(1, 11):
      self.assertFalse(queue.is_empty())
      self.assertEqual(i, queue.peek())
      self.assertEqual(i, queue.remove())

    self.assertTrue(queue.is_empty())


if __name__ == '__main__':
  unittest.main()

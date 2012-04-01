class Node:
  """Container for data."""
  data = None
  next = None
  previous = None

  def __init__(self, data, previous = None, next = None):
    self.data = data
    self.previous = previous
    self.next = next


class Stack:
  """LIFO data structure."""
  __current = None

  def is_empty(self):
    """True if no elements in the stack, False otherwise."""
    return self.__current == None

  def peek(self):
    """Returns the next element in the stack without popping it."""
    return self.__current.data if not self.is_empty() else None

  def push(self, data):
    """Add a new element to the stack."""
    previous = None if self.is_empty() else self.__current
    self.__current = Node(data, previous)

  def pop(self):
    """Get and remove the next element on the stack. Error if empty."""
    if self.is_empty():
      raise Exception('Cannot pop empty stack.')
    node = self.__current
    data = self.__current.data
    self.__current = self.__current.previous
    del node
    return data


class Queue:
  """FIFO data structure."""
  __head = None
  __tail = None

  def insert(self, data):
    """Add a new element to the queue."""
    if self.is_empty():
      self.__head = self.__tail = Node(data)
    else:
      new_tail = Node(data, self.__tail) 
      self.__tail.next = new_tail
      self.__tail = new_tail

  def is_empty(self):
    return self.__head == None

  def peek(self):
    """Returns the next element in the queue without removing it."""
    return self.__head.data if not self.is_empty() else None

  def remove(self):
    """Get and remove the next element from the queue. Error if empty."""
    if self.is_empty():
      raise Exception('Cannot remove from empty queue.')
    data = self.__head.data 
    old_head = self.__head
    self.__head.previous = None
    self.__head = self.__head.next
    del old_head
    return data


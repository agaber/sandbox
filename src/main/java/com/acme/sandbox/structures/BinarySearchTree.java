package com.acme.sandbox.structures;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

public final class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
	private Node<T> root;

	public boolean add(@Nullable T value) {
		if (value == null) {
			return false;
		}
		if (root == null) {
			root = new Node(value);
			return true;
		}
		return add(root, value);
	}

	private boolean add(Node<T> node, T value) {
		int compareTo = node.value.compareTo(value);
		if (compareTo > 0) {
			// Insert left
			if (!node.left.isPresent()) {
				node.left = Optional.of(new Node(value));
				return true;
			} else {
				return add(node.left.get(), value);
			}
		} else if (compareTo < 0) {
			// Insert right
			if (!node.right.isPresent()) {
				node.right = Optional.of(new Node(value));
				return true;
			} else {
				return add(node.right.get(), value);
			}
		}
		// Duplicate value
		return false;
	}

	public void addAll(T... elements) {
		for(T element : elements) {
			add(element);
		}
	}

	public void clear() {
		root = null;
	}

	public Optional<T> search(T value) {
		if (root == null) {
			return Optional.empty();
		}

		Optional<Node<T>> node = Optional.of(root);
		while (node.isPresent()) {
			int compareTo = node.get().value.compareTo(value);
			if (compareTo == 0) {
				return Optional.of(node.get().value);
			}
			node = (compareTo > 0) ? node = node.get().left : node.get().right;
		}

		return Optional.empty();
	}

	@Override
	public Iterator<T> iterator() {
		return new BreadthFirstBinaryTreeIterator(root);
	}

	public void remove(Predicate<T> predicate) {

	}

	public String toString() {
		return "";
	}

	private static class Node<T extends Comparable<T>> {
		Optional<Node<T>> left;
		Optional<Node<T>> right;
		T value;

		Node(Optional<Node<T>> left, Optional<Node<T>> right, T value) {
			this.left = left;
			this.right = right;
			this.value = checkNotNull(value);
		}

		Node(T value) {
			this(Optional.empty(), Optional.empty(), value);
		}
	}

	/** Breadth-first {@link Iterator} over a binary tree. */
	private static class BreadthFirstBinaryTreeIterator<T extends Comparable<T>> implements Iterator<T> {
		private Queue<Node<T>> unvisited = new ArrayDeque<>();

		BreadthFirstBinaryTreeIterator(@Nullable Node<T> root) {
			if (root != null) {
				this.unvisited.offer(root);
			}
		}

		@Override
		public boolean hasNext() {
			return !unvisited.isEmpty();
		}

		@Override
		public T next() {
			Node<T> next = unvisited.poll();
			if (next.left.isPresent()) {
				unvisited.offer(next.left.get());
			}
			if (next.right.isPresent()) {
				unvisited.offer(next.right.get());
			}
			return next.value;
		}
	}
}
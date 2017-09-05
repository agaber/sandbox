package com.acme.sandbox.structures;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
public class BinarySearchTreeTest {
	@Test
	public void iterator() throws Exception {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		bst.addAll(15, 10, 20, 8, 12, 17, 25, 19);
		assertThat(bst).containsExactly(15, 10, 20, 8, 12, 17, 25, 19).inOrder();

		bst.clear();
		bst.addAll(19, 8, 20, 10, 25, 12, 17, 15);
		assertThat(bst).containsExactly(19, 8, 20, 10, 25, 12, 17, 15).inOrder();

		bst.clear();
		bst.addAll(1, 2, 3, 4, 5);
		assertThat(bst).containsExactly(1, 2, 3, 4, 5).inOrder();
	}

	@Test
	public void search() throws Exception {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		assertThat(bst.search(10)).isEqualTo(Optional.empty());

		bst.addAll(50, 30, 20, 40, 70, 60, 80, 75, 85, 65, 32, 34, 36);
		assertThat(bst.search(30)).isEqualTo(Optional.of(30));
		assertThat(bst.search(50)).isEqualTo(Optional.of(50));
		assertThat(bst.search(36)).isEqualTo(Optional.of(36));
		assertThat(bst.search(90)).isEqualTo(Optional.empty());
	}
}

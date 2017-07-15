package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joml.Vector2i;
import org.junit.Test;

import ai.AStarNode;

public class AStarNodeTest {

	@Test
	public void equalsTest() {
		AStarNode node1 = new AStarNode(new Vector2i(5, 5), 0);
		AStarNode node2 = new AStarNode(new Vector2i(5, 5), 0);
		AStarNode node3 = new AStarNode(new Vector2i(4, 5), 0);
		AStarNode node4 = new AStarNode(new Vector2i(5, 4), 0);

		assertTrue(node1.equals(node2));
		assertFalse(node1.equals(node3));
		assertFalse(node1.equals(node4));
	}

	@Test
	public void compareToTest() {

	}
}

package test.ai;

import static org.junit.Assert.assertEquals;

import org.joml.Vector2i;
import org.junit.Test;

import ai.nodes.AStarNode;

public class AStarNodeTest {
	@Test
	public void compareToTest() {
		Vector2i goal = new Vector2i(10, 10);

		AStarNode node1 = new AStarNode(new Vector2i(5, 5), 0, null, goal);
		AStarNode node2 = new AStarNode(new Vector2i(5, 5), 0, null, goal);
		AStarNode node3 = new AStarNode(new Vector2i(4, 4), 0, null, goal);

		assertEquals(0, node1.compareTo(node2));
		assertEquals(-1, node1.compareTo(node3));
		assertEquals(1, node3.compareTo(node1));
	}
}

package test.ai;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.joml.Vector2i;
import org.junit.Test;

import ai.Node;

public class NodeTest {

	@Test
	public void containsWallTest() {
		fail("Not yet implemented");
	}

	@Test
	public void equalsTest() {
		Node node1 = new Node(new Vector2i(5, 5), 0);
		Node node2 = new Node(new Vector2i(5, 5), 0);
		Node node3 = new Node(new Vector2i(4, 5), 0);
		Node node4 = new Node(new Vector2i(5, 4), 0);

		assertTrue(node1.equals(node2));
		assertFalse(node1.equals(node3));
		assertFalse(node1.equals(node4));
	}

}

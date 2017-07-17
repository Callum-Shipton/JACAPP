package ai;

import org.joml.Vector2i;

public class Node {
	protected Vector2i position;
	protected int width;

	public Node(Vector2i position, int width) {
		this.position = position;
		this.width = width;
	}

	public Vector2i getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}
}

package ai;

import math.Vector2;

public class TypeNode extends AStarNode implements Comparable<AStarNode> {

	private String type;

	public TypeNode(Vector2 position, String type) {
		super(position, 1);
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

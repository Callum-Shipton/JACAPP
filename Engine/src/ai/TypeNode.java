package ai;

import math.Vector2;

public class TypeNode extends AStarNode implements Comparable<AStarNode> {

	private int type;

	public TypeNode(Vector2 position, int type) {
		super(position, 1);
		this.type = type;
	}

	public int getType() {
		return type;
	}
}

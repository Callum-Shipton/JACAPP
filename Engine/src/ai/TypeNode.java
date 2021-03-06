package ai;

import org.joml.Vector2i;

public class TypeNode extends AStarNode implements Comparable<AStarNode> {

	private String type;

	public TypeNode(Vector2i position, String type) {
		super(position, 1);
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

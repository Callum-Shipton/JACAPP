package ai.nodes;

import org.joml.Vector2i;

public class TypeNode extends Node {

	private final String type;

	public TypeNode(Vector2i position, int size, String type) {
		super(position, size);
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

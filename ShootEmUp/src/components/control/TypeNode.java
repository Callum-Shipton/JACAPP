package components.control;

import math.Vector2;

public class TypeNode extends Node implements Comparable<Node> {

	private int type; 
	
	public TypeNode(Vector2 position, int type) {
		super(position);
		this.type = type;
	}
	
	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getType() {
		return type;
	}
}

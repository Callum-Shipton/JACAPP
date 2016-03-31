package components.control;

import math.Vector2;

public class Node {
	
	private Vector2 position;
	private Node parent;
	
	public Node(Vector2 position, Node parent){
		
	}

	public Vector2 getPosition() {
		return position;
	}

	public Node getParent() {
		return parent;
	}
	
	public boolean equals(Node node){
		if(node.getPosition().equals(position)){
			return true;
		}
		return false;
	}
}

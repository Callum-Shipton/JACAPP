package components.control;

import components.TypeComponent;
import components.graphical.BaseGraphics;
import level.Map;
import main.ShootEmUp;
import math.Vector2;

public class Node implements Comparable<Node> {

	private Vector2 position;
	private Node parent;
	private Node child;

	public Node(Vector2 position, Node parent) {
		this.position = position;
		this.parent = parent;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Node getParent() {
		return parent;
	}

	public boolean equals(Node node) {
		if (node.getPosition().equals(position)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Node){
			if (((Node)o).getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int compareTo(Node n) {
		Vector2 player = new Vector2(
				(float) Math.floor(
						((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS))
								.getX() / Map.TILE_WIDTH),
		(float) Math.floor(
				((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS)).getY()
						/ Map.TILE_WIDTH));
		
		float distance1 = player.dist(n.getPosition());
		float distance2 = player.dist(getPosition());
		
		return Float.compare(distance2, distance1);
	}
	
	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + position.x());
		hash = (int) ((467 * hash) + position.y());
		return hash;
	}

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}
}

package components.control;

import components.TypeComponent;
import components.graphical.BaseGraphics;
import level.Map;
import main.ShootEmUp;
import math.Vector2;

public class Node implements Comparable<Node> {

	protected Vector2 position;
	private Node parent;
	private Node child;

	public Node(Vector2 position) {
		this.position = position;
	}

	public Node(Vector2 position, Node parent) {
		this(position);
		this.parent = parent;
	}

	@Override
	public int compareTo(Node n) {
		BaseGraphics BG = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		Vector2 player = new Vector2((float) Math.floor(BG.getX() / Map.getTileWidth()),
				(float) Math.floor(BG.getY() / Map.getTileWidth()));

		float distance1 = player.dist(n.getPosition());
		float distance2 = player.dist(getPosition());

		return Float.compare(distance2, distance1);
	}

	public boolean equals(Node node) {
		return node != null && node.getPosition().equals(this.position);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			return ((Node) o).getPosition().equals(this.position);
		}
		return false;
	}

	public Node getChild() {
		return this.child;
	}

	public Node getParent() {
		return this.parent;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + this.position.x());
		hash = (int) ((467 * hash) + this.position.y());
		return hash;
	}

	public void setChild(Node child) {
		this.child = child;
	}
}

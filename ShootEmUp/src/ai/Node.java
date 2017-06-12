package ai;

import components.TypeComponent;
import components.graphical.BaseGraphics;
import level.LevelMap;
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

	public static Vector2 getGridPosition(float x, float y) {
		return new Vector2((float) Math.floor(x / LevelMap.getTileWidth()),
				(float) Math.floor(y / LevelMap.getTileHeight()));
	}

	@Override
	public int compareTo(Node n) {
		BaseGraphics BG = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		Vector2 playerDistance = Node.getGridPosition(BG.getX(), BG.getY());

		float distance1 = playerDistance.dist(n.getPosition());
		float distance2 = playerDistance.dist(getPosition());

		return Float.compare(distance2, distance1);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			return ((Node) o).getPosition().equals(this.position);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + this.position.x());
		hash = (int) ((467 * hash) + this.position.y());
		return hash;
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

	public void setChild(Node child) {
		this.child = child;
	}
}

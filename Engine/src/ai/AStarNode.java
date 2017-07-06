package ai;

import math.Vector2;

public class AStarNode implements Comparable<AStarNode> {

	protected Vector2 position;
	protected int width;
	private AStarNode parent;
	private AStarNode child;
	private Vector2 goal;
	private int parentLength = 0;

	public AStarNode(Vector2 position, int width) {
		this.position = position;
		this.width = width;
	}

	public AStarNode(Vector2 position, int width, AStarNode parent, Vector2 goal) {
		this(position, width);
		this.parent = parent;
		this.goal = goal;
		parentLength = (parent == null)? 0 : parent.getParentLength() + 1;
	}

	@Override
	public int compareTo(AStarNode n) {

		float distance1 = goal.dist(n.getPosition()) + n.getParentLength();
		float distance2 = goal.dist(getPosition()) + parentLength;

		return Float.compare(distance2, distance1);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AStarNode) {
			return ((AStarNode) o).getPosition().equals(position);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + position.x());
		hash = (int) ((467 * hash) + position.y());
		return hash;
	}

	public AStarNode getChild() {
		return child;
	}

	public AStarNode getParent() {
		return parent;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public int getParentLength() {
		return parentLength;
	}

	public void setChild(AStarNode child) {
		this.child = child;
	}

	public int getWidth() {
		return width;
	}
}

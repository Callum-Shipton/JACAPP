package ai;

import math.Vector2;

public class AStarNode implements Comparable<AStarNode> {

	protected Vector2 position;
	protected int width;
	private AStarNode parent;
	private AStarNode child;
	private Vector2 goal;

	public AStarNode(Vector2 position, int width) {
		this.position = position;
		this.width = width;
	}

	public AStarNode(Vector2 position, int width, AStarNode parent, Vector2 goal) {
		this(position, width);
		this.parent = parent;
		this.goal = goal;
	}

	@Override
	public int compareTo(AStarNode n) {

		float distance1 = goal.dist(n.getPosition());
		float distance2 = goal.dist(getPosition());

		return Float.compare(distance2, distance1);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AStarNode) {
			return ((AStarNode) o).getPosition().equals(this.position);
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

	public AStarNode getChild() {
		return this.child;
	}

	public AStarNode getParent() {
		return this.parent;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public void setChild(AStarNode child) {
		this.child = child;
	}
	
	public int getWidth(){
		return width;
	}
}

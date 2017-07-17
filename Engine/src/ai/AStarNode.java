package ai;

import org.joml.Vector2i;

public class AStarNode extends Node implements Comparable<AStarNode> {

	private AStarNode parent;
	private AStarNode child;
	private Vector2i goal;
	private int parentLength = 0;

	public AStarNode(Vector2i position, int width) {
		super(position, width);
	}

	public AStarNode(Vector2i position, int width, AStarNode parent, Vector2i goal) {
		this(position, width);
		this.parent = parent;
		this.goal = goal;
		parentLength = (parent == null) ? 0 : parent.getParentLength() + 1;
	}

	@Override
	public int compareTo(AStarNode n) {

		double distance1 = goal.distance(n.getPosition()) + n.getParentLength();
		double distance2 = goal.distance(getPosition()) + parentLength;

		return Double.compare(distance2, distance1);
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
		hash = (467 * hash) + position.x();
		hash = (467 * hash) + position.y();
		return hash;
	}

	public AStarNode getChild() {
		return child;
	}

	public AStarNode getParent() {
		return parent;
	}

	public int getParentLength() {
		return parentLength;
	}

	public void setChild(AStarNode child) {
		this.child = child;
	}

	@Override
	public int getSize() {
		return size;
	}
}

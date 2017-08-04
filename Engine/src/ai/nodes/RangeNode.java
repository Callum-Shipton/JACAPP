package ai.nodes;

import org.joml.Vector2i;

public class RangeNode extends Node implements Comparable<RangeNode> {

	private int layer;
	private RangeNode parent;

	public RangeNode(Vector2i position) {
		this(position, 0, null);
	}

	public RangeNode(Vector2i position, int layer, RangeNode parent) {
		super(position);
		this.layer = layer;
		this.parent = parent;
	}

	public int getLayer() {
		return layer;
	}

	@Override
	public int compareTo(RangeNode n) {
		if (layer > n.layer) {
			return -1;
		} else if (layer == n.layer) {
			return 0;
		}
		return 1;
	}

	public RangeNode getChild(int x, int y) {
		return new RangeNode(new Vector2i(position.x + x, position.y + y), layer + 1, this);
	}

	public RangeNode getParent() {
		return parent;
	}
}

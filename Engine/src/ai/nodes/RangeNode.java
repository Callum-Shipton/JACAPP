package ai.nodes;

import org.joml.Vector2i;

public class RangeNode extends Node implements Comparable<RangeNode> {

	private int layer;

	public RangeNode(Vector2i position, int size) {
		this(position, 0, size);
	}

	public RangeNode(Vector2i position, int layer, int size) {
		super(position, size);
		this.layer = layer;
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
		return new RangeNode(new Vector2i(position.x + x, position.y + y), layer + 1, size);
	}
}

package ai;

import java.io.Serializable;

import math.Vector2;
import math.Vector4;

public class BoundingBox implements Serializable {

	private static final long serialVersionUID = 5905973252465625721L;

	private Vector4 box;

	public BoundingBox(Vector4 box) {
		this.box = box;
	}

	public void addPoint(Vector2 point) {
		if (point.x() < box.x()) {
			box.x(point.x());
		}
		if (point.y() < box.y()) {
			box.y(point.y());
		}
		if (point.x() > box.z()) {
			box.z(point.x());
		}
		if (point.y() > box.w()) {
			box.w(point.y());
		}
	}

	public boolean boxContains(Vector2 point, int size) {
		return ((point.x() >= box.x()) && (point.x() <= box.z() + (size - 1)))
				&& ((point.y() >= box.y()) && (point.y() <= box.w() + (size - 1)));
	}

	public Vector4 getBox() {
		return box;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BoundingBox) {
			return ((BoundingBox) o).getBox().equals(box);
		}
		return false;
	}

}

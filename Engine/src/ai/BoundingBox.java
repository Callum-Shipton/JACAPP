package ai;

import java.io.Serializable;

import org.joml.Vector4f;

import math.Vector2;

public class BoundingBox implements Serializable {

	private static final long serialVersionUID = 5905973252465625721L;

	private Vector4f box;

	public BoundingBox(Vector4f box) {
		this.box = box;
	}

	public void addPoint(Vector2 point) {
		if (point.x() < box.x()) {
			box.setComponent(0, point.x());
		}
		if (point.y() < box.y()) {
			box.setComponent(1, point.y());
		}
		if (point.x() > box.z()) {
			box.setComponent(2, point.x());
		}
		if (point.y() > box.w()) {
			box.setComponent(3, point.y());
		}
	}

	public boolean boxContains(Vector2 point, int size) {
		return ((point.x() >= box.x()) && (point.x() <= box.z() + (size - 1)))
				&& ((point.y() >= box.y()) && (point.y() <= box.w() + (size - 1)));
	}

	public Vector4f getBox() {
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

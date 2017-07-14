package ai;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector4f;

import math.Vector2;

public class BoundingBox implements Serializable {

	private static final long serialVersionUID = 5905973252465625721L;

	private Vector4f box;

	public BoundingBox(Vector4f box) {
		this.box = box;
	}

	public void addPoint(Vector2 point) {
		if (point.x() < box.getX()) {
			box.setX(point.x());
		}
		if (point.y() < box.getY()) {
			box.setY(point.y());
		}
		if (point.x() > box.getZ()) {
			box.setZ(point.x());
		}
		if (point.y() > box.getW()) {
			box.setW(point.y());
		}
	}

	public boolean boxContains(Vector2 point, int size) {
		return ((point.x() >= box.getX()) && (point.x() <= box.getZ() + (size - 1)))
				&& ((point.y() >= box.getY()) && (point.y() <= box.getW() + (size - 1)));
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

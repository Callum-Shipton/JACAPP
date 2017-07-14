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
			Vector4f box2 = ((BoundingBox) o).getBox();
			if (Float.compare(box.getX(), box2.getX()) != 0) {
				return false;
			}
			if (Float.compare(box.getY(), box2.getY()) != 0) {
				return false;
			}
			if (Float.compare(box.getZ(), box2.getZ()) != 0) {
				return false;
			}
			if (Float.compare(box.getW(), box2.getW()) != 0) {
				return false;
			}
			return true;
		}
		return false;
	}

}

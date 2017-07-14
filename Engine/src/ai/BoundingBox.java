package ai;

import java.io.Serializable;

import org.joml.Vector2i;
import org.joml.Vector4i;

public class BoundingBox implements Serializable {

	private static final long serialVersionUID = 5905973252465625721L;

	private Vector4i box;

	public BoundingBox(Vector4i box) {
		this.box = box;
	}

	public void addPoint(Vector2i point) {
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

	public boolean boxContains(Vector2i point, int size) {
		return ((point.x() >= box.x()) && (point.x() <= box.z() + (size - 1)))
				&& ((point.y() >= box.y()) && (point.y() <= box.w() + (size - 1)));
	}

	public Vector4i getBox() {
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

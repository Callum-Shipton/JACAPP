package components.control;

import math.Vector2;
import math.Vector4;

public class BoundingBox {

	private Vector4 box;

	public BoundingBox(Vector4 box) {
		this.box = box;
	}

	public void addPoint(Vector2 point) {
		if (point.x() < this.box.x()) {
			this.box.x(point.x());
		}
		if (point.y() < this.box.y()) {
			this.box.y(point.y());
		}
		if (point.x() > this.box.z()) {
			this.box.z(point.x());
		}
		if (point.y() > this.box.w()) {
			this.box.w(point.y());
		}
	}

	public boolean boxContains(Vector2 point) {
		if (((point.x() > this.box.x()) && (point.x() < this.box.z()))
				&& ((point.y() > this.box.y()) && (point.y() < this.box.w()))) {
			return true;
		}
		return false;
	}
}

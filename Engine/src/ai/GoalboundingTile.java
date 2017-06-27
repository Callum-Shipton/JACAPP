package ai;

import java.io.Serializable;
import java.util.Map;

public class GoalboundingTile implements Serializable {

	private static final long serialVersionUID = -4680802946409586772L;

	private Map<String, BoundingBox> boxes;

	public GoalboundingTile(Map<String, BoundingBox> boxes) {
		this.boxes = boxes;
	}

	public BoundingBox getBox(String key) {
		return boxes.get(key);
	}
}

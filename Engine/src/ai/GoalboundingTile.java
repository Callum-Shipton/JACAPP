package ai;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GoalboundingTile implements Serializable {

	private static final long serialVersionUID = -4680802946409586772L;

	private Map<String, BoundingBox> boxes;

	public GoalboundingTile(BoundingBox[] boxes) {
		this.boxes = new HashMap<>();

		this.boxes.put("N", boxes[0]);
		this.boxes.put("NW", boxes[1]);
		this.boxes.put("W", boxes[2]);
		this.boxes.put("SW", boxes[3]);
		this.boxes.put("S", boxes[4]);
		this.boxes.put("SE", boxes[5]);
		this.boxes.put("E", boxes[6]);
		this.boxes.put("NE", boxes[7]);
	}

	public BoundingBox getBox(String key) {
		return boxes.get(key);
	}
}

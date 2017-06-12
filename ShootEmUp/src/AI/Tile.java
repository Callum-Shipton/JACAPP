package AI;

public class Tile {
	private BoundingBox North;
	private BoundingBox NorthWest;
	private BoundingBox West;
	private BoundingBox SouthWest;
	private BoundingBox South;
	private BoundingBox SouthEast;
	private BoundingBox East;
	private BoundingBox NorthEast;

	public Tile() {

	}

	public BoundingBox getEast() {
		return this.East;
	}

	public BoundingBox getNorth() {
		return this.North;
	}

	public BoundingBox getNorthEast() {
		return this.NorthEast;
	}

	public BoundingBox getNorthWest() {
		return this.NorthWest;
	}

	public BoundingBox getSouth() {
		return this.South;
	}

	public BoundingBox getSouthEast() {
		return this.SouthEast;
	}

	public BoundingBox getSouthWest() {
		return this.SouthWest;
	}

	public BoundingBox getWest() {
		return this.West;
	}

	public void setBoxes(BoundingBox[] boxes) {
		this.North = boxes[0];
		this.NorthWest = boxes[1];
		this.West = boxes[2];
		this.SouthWest = boxes[3];
		this.South = boxes[4];
		this.SouthEast = boxes[5];
		this.East = boxes[6];
		this.NorthEast = boxes[7];
	}
}

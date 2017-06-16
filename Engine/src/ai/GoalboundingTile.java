package ai;

public class GoalboundingTile {
	private BoundingBox north;
	private BoundingBox northWest;
	private BoundingBox west;
	private BoundingBox southWest;
	private BoundingBox south;
	private BoundingBox southEast;
	private BoundingBox east;
	private BoundingBox northEast;

	public GoalboundingTile(BoundingBox[] boxes) {
		north = boxes[0];
		northWest = boxes[1];
		west = boxes[2];
		southWest = boxes[3];
		south = boxes[4];
		southEast = boxes[5];
		east = boxes[6];
		northEast = boxes[7];
	}

	public BoundingBox getEast() {
		return east;
	}

	public BoundingBox getNorth() {
		return north;
	}

	public BoundingBox getNorthEast() {
		return northEast;
	}

	public BoundingBox getNorthWest() {
		return northWest;
	}

	public BoundingBox getSouth() {
		return south;
	}

	public BoundingBox getSouthEast() {
		return southEast;
	}

	public BoundingBox getSouthWest() {
		return southWest;
	}

	public BoundingBox getWest() {
		return west;
	}

}

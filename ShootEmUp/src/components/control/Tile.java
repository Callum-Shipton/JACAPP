package components.control;

public class Tile {
	BoundingBox North;
	BoundingBox NorthEast;
	BoundingBox East;
	BoundingBox SouthEast;
	BoundingBox South;
	BoundingBox SouthWest;
	BoundingBox West;
	BoundingBox NorthWest;
	
	public Tile(){
		
	}

	public BoundingBox getNorth() {
		return North;
	}

	public void setNorth(BoundingBox north) {
		North = north;
	}

	public BoundingBox getEast() {
		return East;
	}

	public void setEast(BoundingBox east) {
		East = east;
	}

	public BoundingBox getSouth() {
		return South;
	}

	public void setSouth(BoundingBox south) {
		South = south;
	}

	public BoundingBox getWest() {
		return West;
	}

	public void setWest(BoundingBox west) {
		West = west;
	}

	public BoundingBox getNorthEast() {
		return NorthEast;
	}

	public void setNorthEast(BoundingBox northEast) {
		NorthEast = northEast;
	}

	public BoundingBox getSouthEast() {
		return SouthEast;
	}

	public void setSouthEast(BoundingBox southEast) {
		SouthEast = southEast;
	}

	public BoundingBox getSouthWest() {
		return SouthWest;
	}

	public void setSouthWest(BoundingBox southWest) {
		SouthWest = southWest;
	}

	public BoundingBox getNorthWest() {
		return NorthWest;
	}

	public void setNorthWest(BoundingBox northWest) {
		NorthWest = northWest;
	}
}

package components.control;

public class Tile {
	BoundingBox North;
	BoundingBox NorthWest;
	BoundingBox West;
	BoundingBox SouthWest;
	BoundingBox South;
	BoundingBox SouthEast;
	BoundingBox East;
	BoundingBox NorthEast;
	
	public Tile(){
		
	}

	public void setBoxes(BoundingBox[] boxes){
		North  = boxes[0];
		NorthWest  = boxes[1];
		West  = boxes[2];
		SouthWest  = boxes[3];
		South  = boxes[4];
		SouthEast  = boxes[5];
		East  = boxes[6];
		NorthEast  = boxes[7];
	}
	
	public BoundingBox getNorth() {
		return North;
	}

	public BoundingBox getEast() {
		return East;
	}

	public BoundingBox getSouth() {
		return South;
	}

	public BoundingBox getWest() {
		return West;
	}

	public BoundingBox getNorthEast() {
		return NorthEast;
	}
	
	public BoundingBox getSouthEast() {
		return SouthEast;
	}

	public BoundingBox getSouthWest() {
		return SouthWest;
	}

	public BoundingBox getNorthWest() {
		return NorthWest;
	}
}

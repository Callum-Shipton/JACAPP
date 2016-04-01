package components.control;

import math.Vector4;

public class Tile {
	Vector4 North;
	Vector4 NorthEast;
	Vector4 East;
	Vector4 SouthEast;
	Vector4 South;
	Vector4 SouthWest;
	Vector4 West;
	Vector4 NorthWest;
	
	public Tile(){
	}

	public Vector4 getNorth() {
		return North;
	}

	public void setNorth(Vector4 north) {
		North = north;
	}

	public Vector4 getEast() {
		return East;
	}

	public void setEast(Vector4 east) {
		East = east;
	}

	public Vector4 getSouth() {
		return South;
	}

	public void setSouth(Vector4 south) {
		South = south;
	}

	public Vector4 getWest() {
		return West;
	}

	public void setWest(Vector4 west) {
		West = west;
	}

	public Vector4 getNorthEast() {
		return NorthEast;
	}

	public void setNorthEast(Vector4 northEast) {
		NorthEast = northEast;
	}

	public Vector4 getSouthEast() {
		return SouthEast;
	}

	public void setSouthEast(Vector4 southEast) {
		SouthEast = southEast;
	}

	public Vector4 getSouthWest() {
		return SouthWest;
	}

	public void setSouthWest(Vector4 southWest) {
		SouthWest = southWest;
	}

	public Vector4 getNorthWest() {
		return NorthWest;
	}

	public void setNorthWest(Vector4 northWest) {
		NorthWest = northWest;
	}
}

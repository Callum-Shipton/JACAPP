package maze;

import java.util.HashSet;
import java.util.Set;

public class MazeTile {
	Set<Direction> adjacentTiles;
	boolean explored = false;

	public MazeTile() {
		adjacentTiles = new HashSet<>();
	}

	public void setAdjacent(Direction direction) {
		adjacentTiles.add(direction);
	}

	public boolean hasAdjacentTile(Direction direction) {
		return adjacentTiles.contains(direction);
	}

	public Set<Direction> getAdjacentTiles() {
		return adjacentTiles;
	}

	public void setExplored() {
		explored = true;
	}

	public boolean getExplored() {
		return explored;
	}
}

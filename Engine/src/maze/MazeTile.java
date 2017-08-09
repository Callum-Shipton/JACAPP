package maze;

import java.util.HashSet;
import java.util.Set;

public class MazeTile {
	Set<Direction> adjacentTiles;

	public MazeTile() {
		adjacentTiles = new HashSet<>();
	}

	public void setAdjacent(Direction direction) {
		adjacentTiles.add(direction);
	}

	public boolean getAdjacentTile(Direction direction) {
		return adjacentTiles.contains(direction);
	}

	public Set<Direction> getAdjacentTiles() {
		return adjacentTiles;
	}

	public enum Direction {
		N, W, S, E
	}
}

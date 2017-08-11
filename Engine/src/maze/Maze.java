package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.joml.Vector2i;

import logging.Logger;
import logging.Logger.Category;

public class Maze {
	private MazeTile[][] grid;
	private int size;
	private List<Vector2i> open;
	private Set<Vector2i> closed;
	private Vector2i start;
	private Random rand;
	private long seed;

	public Maze(int size) {
		this(size, new Random().nextLong());
	}

	public Maze(int size, long seed) {
		this.seed = seed;
		this.size = size;
		grid = new MazeTile[size][size];
		open = new ArrayList<>();
		closed = new HashSet<>();
		rand = new Random(seed);
	}

	public void generateMaze() {
		start = new Vector2i(size / 2, size / 2);
		open.add(start);

		while (!open.isEmpty()) {
			logState();
			Vector2i nextTile = open.remove(rand.nextInt(open.size()));
			Logger.debug("Current: " + nextTile.x + ", " + nextTile.y, Category.MAZE);
			grid[nextTile.x][nextTile.y] = new MazeTile();
			setWalls(nextTile);
			addAdjacentTiles(nextTile);
			closed.add(nextTile);
		}
	}

	private void addAdjacentTiles(Vector2i position) {
		Set<Vector2i> adjacentTiles = new HashSet<>();
		if (position.y > 0) {
			adjacentTiles.add(new Vector2i(position.x, position.y - 1));
		}
		if (position.x > 0) {
			adjacentTiles.add(new Vector2i(position.x - 1, position.y));
		}
		if (position.y < size - 1) {
			adjacentTiles.add(new Vector2i(position.x, position.y + 1));
		}
		if (position.x < size - 1) {
			adjacentTiles.add(new Vector2i(position.x + 1, position.y));
		}

		for (Vector2i tilePos : adjacentTiles) {
			if (!open.contains(tilePos) && !closed.contains(tilePos)) {
				open.add(tilePos);
			}
		}
	}

	private void setWalls(Vector2i position) {
		List<Direction> directions = new ArrayList<>();
		if (position.x > 0 && grid[position.x - 1][position.y] != null) {
			directions.add(Direction.W);
		}
		if (position.x < size - 1 && grid[position.x + 1][position.y] != null) {
			directions.add(Direction.E);
		}
		if (position.y > 0 && grid[position.x][position.y - 1] != null) {
			directions.add(Direction.N);
		}
		if (position.y < size - 1 && grid[position.x][position.y + 1] != null) {
			directions.add(Direction.S);
		}

		if (!directions.isEmpty()) {
			Direction adjacent = directions.get(rand.nextInt(directions.size()));
			grid[position.x][position.y].setAdjacent(adjacent);

			switch (adjacent) {
			case N:
				grid[position.x][position.y - 1].setAdjacent(Direction.S);
				break;
			case W:
				grid[position.x - 1][position.y].setAdjacent(Direction.E);
				break;
			case S:
				grid[position.x][position.y + 1].setAdjacent(Direction.N);
				break;
			case E:
				grid[position.x + 1][position.y].setAdjacent(Direction.W);
				break;
			}
		}
	}

	private void logState() {
		String output = "";
		output = output.concat("\n");
		output = output.concat("Open: ");
		for (Vector2i pos : open) {
			output = output.concat("(" + pos.x + ", " + pos.y + ") ");
		}
		output = output.concat("\n");
		output = output.concat("Closed: ");
		for (Vector2i pos : closed) {
			output = output.concat("(" + pos.x + ", " + pos.y + ") ");
		}
		output = output.concat("\n");
		Logger.debug(output, Category.MAZE);
	}

	public Vector2i getStart() {
		return start;
	}

	public MazeTile getTile(Vector2i position) {
		return grid[position.x][position.y];
	}

	public MazeTile[][] getGrid() {
		return grid;
	}

	public long getSeed() {
		return seed;
	}
}

package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.joml.Vector2i;

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
		start = new Vector2i(rand.nextInt(size), rand.nextInt(size));
		open.add(start);

		while (!open.isEmpty()) {
			logState();
			Vector2i nextTile = open.remove(rand.nextInt(open.size()));
			System.out.println("Current: " + nextTile.x + ", " + nextTile.y);
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
		List<MazeTile.Direction> directions = new ArrayList<>();
		if (position.x > 0 && grid[position.x - 1][position.y] != null) {
			directions.add(MazeTile.Direction.W);
		}
		if (position.x < size - 1 && grid[position.x + 1][position.y] != null) {
			directions.add(MazeTile.Direction.E);
		}
		if (position.y > 0 && grid[position.x][position.y - 1] != null) {
			directions.add(MazeTile.Direction.N);
		}
		if (position.y < size - 1 && grid[position.x][position.y + 1] != null) {
			directions.add(MazeTile.Direction.S);
		}

		if (!directions.isEmpty()) {
			MazeTile.Direction adjacent = directions.get(rand.nextInt(directions.size()));
			grid[position.x][position.y].setAdjacent(adjacent);

			switch (adjacent) {
			case N:
				grid[position.x][position.y - 1].setAdjacent(MazeTile.Direction.S);
				break;
			case W:
				grid[position.x - 1][position.y].setAdjacent(MazeTile.Direction.E);
				break;
			case S:
				grid[position.x][position.y + 1].setAdjacent(MazeTile.Direction.N);
				break;
			case E:
				grid[position.x + 1][position.y].setAdjacent(MazeTile.Direction.W);
				break;
			}
		}
	}

	private void logState() {
		System.out.println();
		System.out.print("Open: ");
		for (Vector2i pos : open) {
			System.out.print("(" + pos.x + ", " + pos.y + ") ");
		}
		System.out.println();
		System.out.print("Closed: ");
		for (Vector2i pos : closed) {
			System.out.print("(" + pos.x + ", " + pos.y + ") ");
		}
		System.out.println();
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

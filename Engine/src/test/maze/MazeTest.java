package test.maze;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import maze.Direction;
import maze.Maze;
import maze.MazeTile;

public class MazeTest {

	MazeTile[][] grid;

	@Before
	public void setUp() {
		Maze maze = new Maze(10);
		maze.generateMaze();
		grid = maze.getGrid();
	}

	@Test
	public void fullMazeTest() {
		for (int i = 0; i < (grid.length); i++) {
			for (int j = 0; j < (grid.length); j++) {
				if (grid[i][j] == null) {
					fail();
				}
			}
		}
	}

	@Test
	public void testMazeGeneration() {
		printHorEdge();
		System.out.println();

		for (int i = 0; i < grid.length; i++) {
			System.out.print('|');
			for (int j = 0; j < grid.length; j++) {
				System.out.print('0');
				if (j < grid.length - 1) {
					if (grid[j][i].getAdjacentTile(Direction.E)) {
						System.out.print('0');
					} else {
						System.out.print('|');
					}
				}
			}
			System.out.print('|');
			System.out.println();
			if (i < grid.length - 1) {
				System.out.print('+');
				for (int j = 0; j < grid.length; j++) {
					if (grid[j][i].getAdjacentTile(Direction.S)) {
						System.out.print('0');
					} else {
						System.out.print('-');
					}
					if (j < grid.length - 1) {
						System.out.print('+');
					}
				}
				System.out.print('+');
				System.out.println();
			}
		}

		printHorEdge();
	}

	private void printHorEdge() {
		for (int i = 0; i < (grid.length); i++) {
			System.out.print('+');
			System.out.print('-');
		}
		System.out.print('+');
	}
}

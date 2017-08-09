package test.maze;

import org.junit.Test;

import maze.Maze;
import maze.MazeTile;

public class MazeTest {

	@Test
	public void testMazeGeneration() {
		Maze maze = new Maze(5);
		maze.generateMaze();

		MazeTile[][] grid = maze.getGrid();
		for (int i = 0; i < (grid.length * 2) + 2; i++) {
			System.out.print('_');
		}
		System.out.println();

		for (int i = 0; i < grid.length; i++) {
			System.out.print('|');
			for (int j = 0; j < grid.length; j++) {
				System.out.print('0');
				if (grid[i][j].getAdjacentTile(MazeTile.Direction.E)) {
					System.out.print('0');
				} else {
					System.out.print('|');
				}
			}
			System.out.print('|');
			System.out.println();
			System.out.print('|');
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j].getAdjacentTile(MazeTile.Direction.S)) {
					System.out.print('0');
				} else {
					System.out.print('-');
				}
				System.out.print('+');
			}
			System.out.print('|');
			System.out.println();
		}

		for (int i = 0; i < (grid.length * 2) + 2; i++) {
			System.out.print('-');
		}
		System.out.println();
	}
}

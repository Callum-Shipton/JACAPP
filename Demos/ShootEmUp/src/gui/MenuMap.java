package gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joml.Vector2i;

import display.DPDTRenderer;
import display.Image;
import display.ImageProcessor;
import main.ShootEmUp;
import maze.Direction;
import maze.MazeTile;

public class MenuMap extends GuiComponent {

	private Map<Vector2i, Icon> mapTiles = new HashMap<>();
	private static Map<Set<Direction>, Image> mazeIconMap;
	private MazeTile[][] grid;
	private int minY = 0;
	private int minX = 0;
	private int maxY = 0;
	private int maxX = 0;
	private int mapWidth;
	private int mapHeight;

	public MenuMap(float x, float y, float height, float width) {
		super(x, y, height, width);

		if (mazeIconMap == null) {
			initMazeIconMap();
		}

		grid = ShootEmUp.getGame().getMaze().getGrid();
		for (int indexY = 0; indexY < grid.length; indexY++) {
			for (int indexX = 0; indexX < grid[0].length; indexX++) {
				if (grid[indexX][indexY].getExplored()) {
					if (minY == 0) {
						minY = indexY;
					}
					if (indexY > maxY) {
						maxY = indexY;
					}
					if (indexX < minX) {
						minX = indexX;
					}
					if (indexX > maxX) {
						maxX = indexX;
					}
				}
			}
		}
		mapWidth = 11;
		mapHeight = 11;

		for (int indexY = 0; indexY < grid.length; indexY++) {
			for (int indexX = 0; indexX < grid[0].length; indexX++) {
				if (grid[indexX][indexY].getExplored()) {
					setIcon(new Vector2i(indexX, indexY));
				}
			}
		}
	}

	private static void initMazeIconMap() {
		MenuMap.mazeIconMap = new HashMap<>();
		Set<Direction> directions = new HashSet<>();
		directions.add(Direction.N);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconN"));
		directions = new HashSet<>();
		directions.add(Direction.W);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconW"));
		directions = new HashSet<>();
		directions.add(Direction.S);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconS"));
		directions = new HashSet<>();
		directions.add(Direction.E);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconE"));
		directions = new HashSet<>();
		directions.add(Direction.N);
		directions.add(Direction.W);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconNW"));
		directions = new HashSet<>();
		directions.add(Direction.W);
		directions.add(Direction.S);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconWS"));
		directions = new HashSet<>();
		directions.add(Direction.S);
		directions.add(Direction.E);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconSE"));
		directions = new HashSet<>();
		directions.add(Direction.E);
		directions.add(Direction.N);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconEN"));
		directions = new HashSet<>();
		directions.add(Direction.N);
		directions.add(Direction.W);
		directions.add(Direction.S);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconNWS"));
		directions = new HashSet<>();
		directions.add(Direction.W);
		directions.add(Direction.S);
		directions.add(Direction.E);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconWSE"));
		directions = new HashSet<>();
		directions.add(Direction.S);
		directions.add(Direction.E);
		directions.add(Direction.N);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconSEN"));
		directions = new HashSet<>();
		directions.add(Direction.E);
		directions.add(Direction.N);
		directions.add(Direction.W);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconENW"));
		directions = new HashSet<>();
		directions.add(Direction.N);
		directions.add(Direction.W);
		directions.add(Direction.S);
		directions.add(Direction.E);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconNWSE"));
		directions = new HashSet<>();
		directions.add(Direction.N);
		directions.add(Direction.S);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconNS"));
		directions = new HashSet<>();
		directions.add(Direction.W);
		directions.add(Direction.E);
		mazeIconMap.put(directions, ImageProcessor.getImage("MapIconWE"));
	}

	private void setIcon(Vector2i position) {
		Set<Direction> tileDirections = grid[position.x][position.y].getAdjacentTiles();
		float scale = (width / mapWidth) / 4;
		float gap = width / mapWidth;
		mapTiles.put(position, new Icon(x + (position.x * gap), y + (position.y * gap), mazeIconMap.get(tileDirections),
				false, scale));
	}

	@Override
	public void render(DPDTRenderer d) {
		for (Icon icon : mapTiles.values()) {
			icon.render(d);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

}

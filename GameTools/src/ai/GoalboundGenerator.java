package ai;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.joml.Vector2i;
import org.joml.Vector4i;

import logging.Logger;

public class GoalboundGenerator {

	private Map<Integer, GoalboundingTile[][]> goalboundingMaps;
	private BufferedImage mapImage;
	private Set<Vector2i> walls;

	private static final int MAXIMUM_SIZE = 4;
	private static final String IN_MAP_FILE = "/Levels/Level3.png";
	private static final String OUT_MAP_FILE = "../ShootEmUp/res/Levels/Level3.bound";

	private static final int BROWNWALL_COLOR = -7864299;
	private static final int GREYWALL_COLOR = -8421505;
	private static final int LIGHTWATER_COLOR = -16735512;
	private static final int DARKWATER_COLOR = -12629812;

	private static final int GRASS_COLOR = -4856291;
	private static final int PATH_COLOR = -1055568;

	public GoalboundGenerator() {
		loadMap(IN_MAP_FILE);
		goalboundingMaps = new ConcurrentHashMap<>();

		for (int i = 1; i <= MAXIMUM_SIZE; i++) {
			goalboundingMaps.put(i, new GoalboundingTile[mapImage.getWidth()][mapImage.getHeight()]);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		GoalboundGenerator generator = new GoalboundGenerator();
		generator.generateGoalbounder();

	}

	public void generateGoalbounder() throws InterruptedException {
		getWalls();
		createGoalBoundingBoxes(mapImage.getWidth(), mapImage.getHeight());
		Logger.info("Saving.....");
		saveGoalbounder();
		Logger.info("Finished");
	}

	private void saveGoalbounder() {
		GoalBounder goalbounder = new GoalBounder(goalboundingMaps);

		try (FileOutputStream fileOut = new FileOutputStream(OUT_MAP_FILE)) {
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(goalbounder);
			out.close();
			fileOut.close();
			Logger.info("Serialized data is saved in " + OUT_MAP_FILE);
		} catch (IOException i) {
			Logger.error(i);
		}
	}

	private void getWalls() {
		Logger.info("Generating Walls");
		walls = new HashSet<>();
		for (int y = 0; y < mapImage.getHeight(); y++) {
			for (int x = 0; x < mapImage.getWidth(); x++) {
				switch (mapImage.getRGB(x, y)) {
				case BROWNWALL_COLOR:
				case GREYWALL_COLOR:
				case LIGHTWATER_COLOR:
				case DARKWATER_COLOR:
					walls.add(new Vector2i(x, y));
					break;
				case GRASS_COLOR:
				case PATH_COLOR:
					break;
				default:
					Logger.warn("Tile type not found");
				}
			}
		}
		Logger.info("Walls Generated");
	}

	private void loadMap(String fileLocation) {
		try {
			mapImage = ImageIO.read(getClass().getResource(fileLocation));
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	private List<TypeNode> generateChildNodes(AStarNode startNode, int size) {
		return generateChildNodes(startNode, null, size);
	}

	private List<TypeNode> generateChildNodes(AStarNode startNode, String type, int size) {
		List<TypeNode> childNodes = new ArrayList<>();

		int startX = startNode.getPosition().x();
		int startY = startNode.getPosition().y();

		if (!AStarSearch.movesIntoWall(new Vector2i(startX, startY - 1), size, walls, "N")) {
			childNodes.add(new TypeNode(new Vector2i(startX, startY - 1), type != null ? type : "N"));
		}
		if (!AStarSearch.movesIntoWall(new Vector2i(startX - 1, startY), size, walls, "W")) {
			childNodes.add(new TypeNode(new Vector2i(startX - 1, startY), type != null ? type : "W"));
		}
		if (!AStarSearch.movesIntoWall(new Vector2i(startX, startY + 1), size, walls, "S")) {
			childNodes.add(new TypeNode(new Vector2i(startX, startY + 1), type != null ? type : "S"));
		}
		if (!AStarSearch.movesIntoWall(new Vector2i(startX + 1, startY), size, walls, "E")) {
			childNodes.add(new TypeNode(new Vector2i(startX + 1, startY), type != null ? type : "E"));
		}

		if (!AStarSearch.movesIntoWall(new Vector2i(startX - 1, startY - 1), size, walls, "NW")) {
			childNodes.add(new TypeNode(new Vector2i(startX - 1, startY - 1), type != null ? type : "NW"));
		}
		if (!AStarSearch.movesIntoWall(new Vector2i(startX - 1, startY + 1), size, walls, "SW")) {
			childNodes.add(new TypeNode(new Vector2i(startX - 1, startY + 1), type != null ? type : "SW"));
		}
		if (!AStarSearch.movesIntoWall(new Vector2i(startX + 1, startY + 1), size, walls, "SE")) {
			childNodes.add(new TypeNode(new Vector2i(startX + 1, startY + 1), type != null ? type : "SE"));
		}
		if (!AStarSearch.movesIntoWall(new Vector2i(startX + 1, startY - 1), size, walls, "NE")) {
			childNodes.add(new TypeNode(new Vector2i(startX + 1, startY - 1), type != null ? type : "NE"));
		}

		return childNodes;
	}

	private Map<String, BoundingBox> initBoundingBoxes(List<TypeNode> childNodes) {
		Map<String, BoundingBox> boxes = new HashMap<>();

		for (TypeNode node : childNodes) {
			boxes.put(node.getType(), new BoundingBox(new Vector4i(node.getPosition().x(), node.getPosition().y(),
					node.getPosition().x(), node.getPosition().y())));
		}
		return boxes;
	}

	private void addNodesToQueue(List<TypeNode> nodes, Queue<TypeNode> open, Set<TypeNode> closed) {
		for (TypeNode node : nodes) {
			if (!closed.contains(node)) {
				open.add(node);
				closed.add(node);
			}
		}
	}

	private void fillMap(Queue<TypeNode> open, Set<TypeNode> closed, Map<String, BoundingBox> boxes, int size) {
		while (!open.isEmpty()) {
			TypeNode current = open.poll();

			String currentType = current.getType();

			boxes.get(currentType).addPoint(current.getPosition());

			List<TypeNode> childNodes = generateChildNodes(current, currentType, size);

			addNodesToQueue(childNodes, open, closed);
		}

	}

	private void createGoalBoundingBoxes(int mapWidth, int mapHeight) throws InterruptedException {
		int threads = Runtime.getRuntime().availableProcessors();

		ExecutorService pool = Executors.newFixedThreadPool(threads);
		for (int size = 1; size <= MAXIMUM_SIZE; size++) {

			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {
					pool.execute(new OneShotTask(size, x, y));

				}
				Logger.info("Layer " + (x + 1) + " of " + mapWidth + " completed");
			}
			Logger.info("Size " + size + " completed");
		}

		pool.shutdown();

		// Blocks until all tasks have completed execution after a shutdown
		// request
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		Logger.info("All goalbounds Created");
	}

	class OneShotTask implements Runnable {
		int size;
		int x;
		int y;

		OneShotTask(int size, int x, int y) {
			this.size = size;
			this.x = x;
			this.y = y;
		}

		@Override
		public void run() {
			if (!containsWall(new Vector2i(x, y), size, walls)) {

				// queue for tiles to be looked at
				Queue<TypeNode> open = new LinkedList<>();

				// list of already viewed tiles
				Set<TypeNode> closed = new HashSet<>();

				AStarNode start = new AStarNode(new Vector2i(x, y), size);

				List<TypeNode> startingNodes = generateChildNodes(start, size);

				for (TypeNode node : startingNodes) {
					open.add(node);
					closed.add(node);
				}

				Map<String, BoundingBox> boxes = initBoundingBoxes(startingNodes);

				fillMap(open, closed, boxes, size);

				goalboundingMaps.get(size)[x][y] = new GoalboundingTile(boxes);

			}
		}
	}

	private static boolean containsWall(Vector2i position, int size, Set<Vector2i> walls) {
		for (int i = (int) position.x(); i < position.x() + size; i++) {
			for (int j = (int) position.y(); j < position.y() + size; j++) {
				if (walls.contains(new Vector2i(i, j))) {
					return true;
				}
			}
		}
		return false;
	}
}

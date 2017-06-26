package ai;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.imageio.ImageIO;

import logging.Logger;
import math.Vector2;
import math.Vector4;

public class GoalboundGenerator {
	
	private Map<Integer, GoalboundingTile[][]> goalboundingMaps;
	private BufferedImage mapImage;
	private Set<Vector2> walls;
	
	private static final int MAXIMUM_SIZE = 4;
	private static final String MAP_FILE = "/Levels/Level1.png";
	
	private static final int BROWNWALL_COLOR = -7864299;
	private static final int GREYWALL_COLOR = -8421505;
	private static final int LIGHTWATER_COLOR = -16735512;
	private static final int DARKWATER_COLOR = -12629812;
	
	public GoalboundGenerator(){
		loadMap(MAP_FILE);
		goalboundingMaps = new HashMap<>();
		for (int i = 1; i <= MAXIMUM_SIZE; i++) {
			goalboundingMaps.put(i, new GoalboundingTile[mapImage.getWidth()][mapImage.getHeight()]);
		}
	}
	
	public static void main(String[] args){
		GoalboundGenerator generator = new GoalboundGenerator();
		generator.generateGoalbounder();
		
	}
	
	public void generateGoalbounder(){
		getWalls();
		createGoalBoundingBoxes(walls, mapImage.getWidth(), mapImage.getHeight());
		saveGoalbounder();
	}
	
	private void saveGoalbounder(){
		GoalBounder goalbounder = new GoalBounder(goalboundingMaps);
		
		 try (FileOutputStream fileOut = new FileOutputStream("/res/goalbounding/goalbound.ser")){
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(goalbounder);
	         out.close();
	         fileOut.close();
	         Logger.info("Serialized data is saved in /res/goalbounding/goalbound.ser");
	      }catch(IOException i) {
	    	  Logger.error(i);
	      } 
	}
	
	private void getWalls(){
		walls = new HashSet<>();
		for (int y = 0; y < mapImage.getHeight(); y++) {
			for (int x = 0; x < mapImage.getWidth(); x++) {
				switch (mapImage.getRGB(x, y)) {
				case BROWNWALL_COLOR:
				case GREYWALL_COLOR:
				case LIGHTWATER_COLOR:
				case DARKWATER_COLOR:
					walls.add(new Vector2(x,y));
				default:
					Logger.warn("Unknown tile code: " + mapImage.getRGB(x, y));
				}
			}
		}
	}
	
	private void loadMap(String fileLocation) {
		try {
			mapImage = ImageIO.read(getClass().getResource(fileLocation));
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	private TypeNode[] generateChildNodes(AStarNode startNode) {
		return generateChildNodes(startNode, null);
	}

	private TypeNode[] generateChildNodes(AStarNode startNode, Integer type) {
		TypeNode[] childNodes = new TypeNode[8];

		float startX = startNode.getPosition().x();
		float startY = startNode.getPosition().y();

		childNodes[0] = new TypeNode(new Vector2(startX, startY - 1), type != null ? type : 0);
		childNodes[1] = new TypeNode(new Vector2(startX - 1, startY - 1), type != null ? type : 1);
		childNodes[2] = new TypeNode(new Vector2(startX - 1, startY), type != null ? type : 2);
		childNodes[3] = new TypeNode(new Vector2(startX - 1, startY + 1), type != null ? type : 3);
		childNodes[4] = new TypeNode(new Vector2(startX, startY + 1), type != null ? type : 4);
		childNodes[5] = new TypeNode(new Vector2(startX + 1, startY + 1), type != null ? type : 5);
		childNodes[6] = new TypeNode(new Vector2(startX + 1, startY), type != null ? type : 6);
		childNodes[7] = new TypeNode(new Vector2(startX + 1, startY - 1), type != null ? type : 7);

		return childNodes;
	}

	private BoundingBox[] initBoundingBoxes(TypeNode[] childNodes) {
		BoundingBox[] boxes = new BoundingBox[childNodes.length];
		int nodeCount = 0;
		for (TypeNode node : childNodes) {
			boxes[nodeCount] = new BoundingBox(new Vector4(node.getPosition().x(), node.getPosition().y(), 0, 0));
			nodeCount++;
		}
		return boxes;
	}

	private void addNodesToQueue(TypeNode[] nodes, Set<Vector2> walls, int size, Queue<TypeNode> open,
			Set<TypeNode> closed) {
		for (int i = 0; i < nodes.length; i++) {
			if (!closed.contains(nodes[i])) {
				if (!AStarSearch.containsWall(nodes[i].position, size, walls)) {
					open.add(nodes[i]);
				}
				closed.add(nodes[i]);
			}
		}
	}

	private void fillMap(Queue<TypeNode> open, Set<TypeNode> closed, BoundingBox[] boxes, Set<Vector2> walls,
			int size) {
		while (!open.isEmpty()) {
			TypeNode current = open.poll();

			int currentType = current.getType();

			boxes[currentType].addPoint(current.getPosition());

			TypeNode[] childNodes = generateChildNodes(current, currentType);

			addNodesToQueue(childNodes, walls, size, open, closed);
		}

	}

	private void createGoalBoundingBoxes(Set<Vector2> walls, int mapWidth, int mapHeight) {
		for (int size = 1; size <= MAXIMUM_SIZE; size++) {

			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {

					if (!AStarSearch.containsWall(new Vector2(x, y), size, walls)) {

						// queue for tiles to be looked at
						Queue<TypeNode> open = new LinkedList<>();

						// list of already viewed tiles
						Set<TypeNode> closed = new HashSet<>();

						AStarNode start = new AStarNode(new Vector2(x, y), 1);

						TypeNode[] startingNodes = generateChildNodes(start);

						for (TypeNode node : startingNodes) {
							open.add(node);
							closed.add(node);
						}

						BoundingBox[] boxes = initBoundingBoxes(startingNodes);

						fillMap(open, closed, boxes, walls, size);

						goalboundingMaps.get(size)[x][y] = new GoalboundingTile(boxes);
					}
				}
			}
		}
	}
}

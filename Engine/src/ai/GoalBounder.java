package ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import logging.Logger;
import logging.Logger.Category;
import math.Vector2;
import math.Vector4;

public class GoalBounder {
	private Map<Integer, GoalboundingTile[][]> goalboundingMaps;
	private static int maximumSize = 4;

	public GoalBounder(int width, int height, Set<Vector2> walls) {
		goalboundingMaps = new HashMap<>();
		for (int i = 1; i <= maximumSize; i++) {
			goalboundingMaps.put(i, new GoalboundingTile[width][height]);
		}
		createGoalBoundingBoxes(walls, width, height);
	}

	public GoalboundingTile getTile(float x, float y, float size) {
		Logger.debug("X:" + x + " Y:" + y + " Size:" + size, Category.AI_GOALBOUNDING);
		return goalboundingMaps.get((int) size)[(int) x][(int) y];
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
				if (!containsWall(nodes[i].position, size, walls)) {
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
		for (int size = 1; size <= maximumSize; size++) {

			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {

					if (!containsWall(new Vector2(x, y), size, walls)) {

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

	public static boolean containsWall(Vector2 position, int size, Set<Vector2> walls) {
		for (int i = (int) position.x(); i < position.x() + size; i++) {
			for (int j = (int) position.y(); j < position.y() + size; j++) {
				if (walls.contains(new Vector2(i, j))) {
					return true;
				}
			}
		}
		return false;
	}
}

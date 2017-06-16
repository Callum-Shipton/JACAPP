package ai;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import math.Vector2;
import math.Vector4;

public class GoalBounder {
	private GoalboundingTile[][] aiTiles;

	public GoalBounder(int width, int height, Set<Vector2> walls) {
		this.aiTiles = new GoalboundingTile[width][height];
		createGoalBoundingBoxes(walls);
	}

	public GoalboundingTile getTile(float x, float y) {
		return aiTiles[(int) x][(int) y];
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

	private void addNodesToQueues(TypeNode[] childNodes, Queue<TypeNode> open, Set<TypeNode> closed) {
		for (TypeNode node : childNodes) {
			open.add(node);
			closed.add(node);
		}
	}

	private void addNodesToQueue(TypeNode[] nodes, Set<Vector2> walls, Queue<TypeNode> open,
			Set<TypeNode> closed) {
		for (int i = 0; i < nodes.length; i++) {
			if (!closed.contains(nodes[i])) {
				if (!walls.contains(nodes[i].position)) {
					open.add(nodes[i]);
				}
				closed.add(nodes[i]);
			}
		}
	}

	private void fillMap(Queue<TypeNode> open, Set<TypeNode> closed, BoundingBox[] boxes, Set<Vector2> walls) {
		while (!open.isEmpty()) {
			TypeNode current = open.poll(); // Tile current being
											// checked
			int currentType = current.getType();

			boxes[currentType].addPoint(current.getPosition());

			TypeNode[] childNodes = generateChildNodes(current, currentType);

			addNodesToQueue(childNodes, walls, open, closed);
		}

	}

	private void createGoalBoundingBoxes(Set<Vector2> walls) {
		for (int x = 2; x < (aiTiles.length - 2); x++) {
			for (int y = 2; y < (aiTiles[0].length - 2); y++) {

				if (!walls.contains(new Vector2(x, y))) {

					// queue for tiles to be looked at
					Queue<TypeNode> open = new LinkedList<>();

					// list of already viewed tiles
					Set<TypeNode> closed = new HashSet<>();

					AStarNode start = new AStarNode(new Vector2(x, y));

					TypeNode[] startingNodes = generateChildNodes(start);
					addNodesToQueues(startingNodes, open, closed);

					BoundingBox[] boxes = initBoundingBoxes(startingNodes);

					fillMap(open, closed, boxes, walls);

					aiTiles[x][y] = new GoalboundingTile(boxes);

				}
			}
		}
	}
}
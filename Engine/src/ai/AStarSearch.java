package ai;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import logging.Logger;
import math.Vector2;

public class AStarSearch {

	private static Set<Vector2> walls;
	private static GoalBounder goalBounder;
	private Queue<AStarNode> openNodes;
	private Set<AStarNode> closedNodes;
	private AStarNode startNode;
	private Map<String, AStarNode> childNodes;
	private AStarNode goalNode;
	private float nodeWidth;
	private int width;
	private Deque<Vector2> path;
	private Vector2 target;

	public AStarSearch(Set<Vector2> walls, GoalBounder goalBounder, float nodeWidth, int width) {
		if (AStarSearch.walls == null) {
			AStarSearch.walls = walls;
		}
		if (AStarSearch.goalBounder == null) {
			AStarSearch.goalBounder = goalBounder;
		}
		this.nodeWidth = nodeWidth;
		this.width = width;
	}

	private void initData(Vector2 goal, Vector2 start) {

		startNode = new AStarNode(start, width);
		goalNode = new AStarNode(goal, width);
		openNodes = new PriorityQueue<>(); // queue for nodes to be searched
		closedNodes = new HashSet<>(); // list of nodes already searched or
										// being
										// searched
		childNodes = new HashMap<>();
		openNodes.add(startNode);
		closedNodes.add(startNode);
	}

	private void generateChildNodes(AStarNode currentNode) {
		float currentX = currentNode.getPosition().x();
		float currentY = currentNode.getPosition().y();
		Vector2 goalPosition = goalNode.getPosition();

		childNodes.put("N", new AStarNode(new Vector2(currentX, currentY - 1), width, currentNode, goalPosition));
		childNodes.put("NW", new AStarNode(new Vector2(currentX - 1, currentY - 1), width, currentNode, goalPosition));
		childNodes.put("W", new AStarNode(new Vector2(currentX - 1, currentY), width, currentNode, goalPosition));
		childNodes.put("SW", new AStarNode(new Vector2(currentX - 1, currentY + 1), width, currentNode, goalPosition));
		childNodes.put("S", new AStarNode(new Vector2(currentX, currentY + 1), width, currentNode, goalPosition));
		childNodes.put("SE", new AStarNode(new Vector2(currentX + 1, currentY + 1), width, currentNode, goalPosition));
		childNodes.put("E", new AStarNode(new Vector2(currentX + 1, currentY), width, currentNode, goalPosition));
		childNodes.put("NE", new AStarNode(new Vector2(currentX + 1, currentY - 1), width, currentNode, goalPosition));
	}

	private void addNode(String key, GoalboundingTile goalboundingTile) {
		AStarNode node = childNodes.get(key);
		if (!closedNodes.contains(node) && !movesIntoWall(node.getPosition(), node.getWidth(), walls, key)
				&& goalboundingTile.getBox(key).boxContains(goalNode.getPosition())) {
			openNodes.add(node);
			closedNodes.add(node);
		}
	}

	private void addUnobstructedChildNodes(Vector2 position) {
		GoalboundingTile goalboundingTile = goalBounder.getTile(position.x(), position.y(), width);
		if (goalboundingTile == null) {
			Logger.debug("no goalboundingTile at:" + position.x() + ", " + position.y(),
					Logger.Category.AI_GOALBOUNDING);
		}

		addNode("N", goalboundingTile);
		addNode("NW", goalboundingTile);
		addNode("W", goalboundingTile);
		addNode("SW", goalboundingTile);
		addNode("S", goalboundingTile);
		addNode("SE", goalboundingTile);
		addNode("E", goalboundingTile);
		addNode("NE", goalboundingTile);
	}

	private Vector2 findPathStart(AStarNode currentNode) {
		AStarNode node = currentNode;
		path = new LinkedList<>();
		path.push(node.getPosition());
		while (true) {
			if (node.getParent() != null) {
				if (node.getParent().equals(startNode)) {
					target = node.getPosition();
					return node.getPosition();
				} else {
					node = node.getParent();
				}
			} else {
				return new Vector2(0, 0);
			}
			path.push(node.getPosition());
		}
	}

	public Vector2 findPath(Vector2 goal, Vector2 start) {

		if (goalNode != null && goal.equals(goalNode.getPosition())) {
			if (start.equals(target) && !path.isEmpty()) {
				target = path.pop();
			}
		} else {

			initData(goal, start);

			int searchedNodes = 0;
			while (!openNodes.isEmpty()) {
				searchedNodes++;
				AStarNode currentNode = openNodes.poll();

				if (containsGoal(currentNode.getPosition(), currentNode.getWidth(), goalNode.getPosition())) {
					return findPathStart(currentNode);
				}

				generateChildNodes(currentNode);

				addUnobstructedChildNodes(currentNode.getPosition());
			}

			Logger.warn("cannot find player");
			Logger.debug(searchedNodes, Logger.Category.AI);
		}
		if (target != null) {
			return target;
		}
		return new Vector2(0, 0);
	}

	public boolean containsGoal(Vector2 origin, int size, Vector2 goal) {

		for (int i = (int) origin.x(); i < origin.x() + size; i++) {
			for (int j = (int) origin.y(); j < origin.y() + size; j++) {
				if (goal.equals(new Vector2(i, j))) {
					return true;
				}
			}
		}
		return false;
	}

	public Vector2 getGridPosition(float x, float y) {
		return new Vector2((float) Math.floor(x / nodeWidth), (float) Math.floor(y / nodeWidth));
	}

	public static boolean movesIntoWall(Vector2 position, int size, Set<Vector2> walls, String key) {
		switch (key) {
		case "NW":
			return checkEdgesForWalls(position, size + 1, walls, "N")
					|| checkEdgesForWalls(position, size + 1, walls, "W");
		case "NE":
			return checkEdgesForWalls(position, size + 1, walls, "N")
					|| checkEdgesForWalls(position, size + 1, walls, "E");
		case "SW":
			return checkEdgesForWalls(position, size + 1, walls, "S")
					|| checkEdgesForWalls(position, size + 1, walls, "W");
		case "SE":
			return checkEdgesForWalls(position, size + 1, walls, "S")
					|| checkEdgesForWalls(position, size + 1, walls, "E");
		default:
			return checkEdgesForWalls(position, size, walls, key);
		}

	}

	private static boolean checkEdgesForWalls(Vector2 position, int size, Set<Vector2> walls, String key) {
		switch (key) {
		case "N":
			for (int i = (int) position.x(); i < position.x() + size; i++) {
				if (walls.contains(new Vector2(i, position.y()))) {
					return true;
				}
			}
			break;
		case "S":
			for (int i = (int) position.x(); i < position.x() + size; i++) {
				if (walls.contains(new Vector2(i, position.y() + size - 1))) {
					return true;
				}
			}
			break;
		case "W":
			for (int j = (int) position.y(); j < position.y() + size; j++) {
				if (walls.contains(new Vector2(position.x(), j))) {
					return true;
				}
			}
			break;
		case "E":
			for (int j = (int) position.y(); j < position.y() + size; j++) {
				if (walls.contains(new Vector2(position.x() + size - 1, j))) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	public Vector2 nextNode() {
		return path.pop();
	}
}

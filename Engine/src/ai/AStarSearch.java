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

	private Set<Vector2> walls;
	private GoalBounder goalBounder;

	private Queue<AStarNode> openNodes = new PriorityQueue<>();
	private Set<AStarNode> closedNodes = new HashSet<>();
	private Map<String, AStarNode> childNodes = new HashMap<>();
	private Deque<Vector2> path = new LinkedList<>();

	private AStarNode startNode;
	private AStarNode goalNode;
	private Vector2 target;
	private int width;

	private boolean playerOutOfReach = false;

	private float nodeWidth;

	public AStarSearch(Set<Vector2> walls, GoalBounder goalBounder, float nodeWidth, int width) {
		this.walls = walls;
		this.goalBounder = goalBounder;

		this.nodeWidth = nodeWidth;
		this.width = width;
		// searched
	}

	private void initData(Vector2 goal, Vector2 start) {

		startNode = new AStarNode(start, width);
		goalNode = new AStarNode(goal, width);

		openNodes.clear(); // queue for nodes to be searched
		closedNodes.clear(); // list of nodes already searched or being searched
		path.clear();
		childNodes.clear();

		openNodes.add(startNode);
		closedNodes.add(startNode);

		playerOutOfReach = false;
	}

	private void generateChildNodes(AStarNode currentNode) {
		float currentX = currentNode.getPosition().x();
		float currentY = currentNode.getPosition().y();
		Vector2 goalPosition = goalNode.getPosition();

		childNodes.clear();
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
				&& goalboundingTile.getBox(key).boxContains(goalNode.getPosition(), width)) {
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
		path.push(node.getPosition());
		while (true) {
			if (node.getParent() != null) {
				if (node.getParent().equals(startNode)) {
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
			} else if (path.isEmpty() && !playerOutOfReach) {
				target = goal;
			}
		} else {

			initData(goal, start);

			int searchedNodes = 0;
			while (!openNodes.isEmpty()) {
				searchedNodes++;
				AStarNode currentNode = openNodes.poll();

				if (containsGoal(currentNode.getPosition(), currentNode.getWidth(), goalNode.getPosition())) {
					target = findPathStart(currentNode);
					return target;
				}

				generateChildNodes(currentNode);

				addUnobstructedChildNodes(currentNode.getPosition());
			}

			Logger.warn("cannot find player");
			Logger.debug("Nodes Searched: " + searchedNodes, Logger.Category.AI);
			playerOutOfReach = true;
			target = new Vector2(0, 0);
		}
		return target;

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
			return checkEdgesForWalls(new Vector2(position.x() - 1, position.y()), size + 1, walls, "N")
					|| checkEdgesForWalls(new Vector2(position.x() - 1, position.y()), size + 1, walls, "E");
		case "SW":
			return checkEdgesForWalls(new Vector2(position.x(), position.y() - 1), size + 1, walls, "S")
					|| checkEdgesForWalls(new Vector2(position.x(), position.y() - 1), size + 1, walls, "W");
		case "SE":
			return checkEdgesForWalls(new Vector2(position.x() - 1, position.y() - 1), size + 1, walls, "S")
					|| checkEdgesForWalls(new Vector2(position.x() - 1, position.y() - 1), size + 1, walls, "E");
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

}

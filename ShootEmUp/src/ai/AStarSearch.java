package ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import level.LevelMap;
import main.Logger;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class AStarSearch {

	private static Map<Vector2, Entity> walls;
	private static GoalBounder goalBounder;
	private Queue<Node> openNodes;
	private Set<Node> closedNodes;
	private Node startNode;
	private Map<String, Node> childNodes;
	private Node goalNode;

	public AStarSearch(Node goalNode, Node startNode) {
		this.startNode = startNode;
		this.goalNode = goalNode;

		LevelMap map = ShootEmUp.getCurrentLevel().getMap();
		if (walls == null) {
			walls = map.getWalls();
		}
		if (goalBounder == null) {
			goalBounder = map.getGoalBounder();
		}
	}

	private void initData() {
		openNodes = new PriorityQueue<>(); // queue for nodes to be searched
		closedNodes = new HashSet<>(); // list of nodes already searched or
										// being
										// searched
		childNodes = new HashMap<>();
		openNodes.add(startNode);
		closedNodes.add(startNode);
	}

	private void generateChildNodes(Node currentNode) {
		float currentX = currentNode.getPosition().x();
		float currentY = currentNode.getPosition().y();

		childNodes.put("N", new Node(new Vector2(currentX, currentY - 1), currentNode));
		childNodes.put("NW", new Node(new Vector2(currentX - 1, currentY - 1), currentNode));
		childNodes.put("W", new Node(new Vector2(currentX - 1, currentY), currentNode));
		childNodes.put("SW", new Node(new Vector2(currentX - 1, currentY + 1), currentNode));
		childNodes.put("SSW", new Node(new Vector2(currentX - 1, currentY + 2), currentNode));
		childNodes.put("S", new Node(new Vector2(currentX, currentY + 1), currentNode));
		childNodes.put("SS", new Node(new Vector2(currentX, currentY + 2), currentNode));
		childNodes.put("SSE", new Node(new Vector2(currentX + 1, currentY + 2), currentNode));
		childNodes.put("SSEE", new Node(new Vector2(currentX + 2, currentY + 2), currentNode));
		childNodes.put("SE", new Node(new Vector2(currentX + 1, currentY + 1), currentNode));
		childNodes.put("SEE", new Node(new Vector2(currentX + 2, currentY + 1), currentNode));
		childNodes.put("EE", new Node(new Vector2(currentX + 2, currentY), currentNode));
		childNodes.put("NEE", new Node(new Vector2(currentX + 2, currentY - 1), currentNode));
		childNodes.put("E", new Node(new Vector2(currentX + 1, currentY), currentNode));
		childNodes.put("NE", new Node(new Vector2(currentX + 1, currentY - 1), currentNode));

		goalBounder.getTile(currentX, currentY);
	}

	private void addUnobstructedChildNodes() {
		if (!closedNodes.contains(childNodes.get("N"))) {
			if (isNodeNotWall(childNodes.get("N")) && isNodeNotWall(childNodes.get("NE"))) {
				// if (currentTile.getNorth().boxContains(goal)) {
				openNodes.add(childNodes.get("N"));
				closedNodes.add(childNodes.get("N"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("SSW"))) {
			if (isNodeNotWall(childNodes.get("SSW")) && isNodeNotWall(childNodes.get("N"))
					&& isNodeNotWall(childNodes.get("W"))
					&& ((isNodeNotWall(childNodes.get("SW")) || isNodeNotWall(childNodes.get("NE"))))) {
				// if (currentTile.getNorthWest().boxContains(goal)) {
				openNodes.add(childNodes.get("SSW"));
				closedNodes.add(childNodes.get("SSW"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("W"))) {
			if (isNodeNotWall(childNodes.get("W")) && isNodeNotWall(childNodes.get("SW"))) {
				// if (currentTile.getWest().boxContains(goal)) {
				openNodes.add(childNodes.get("W"));
				closedNodes.add(childNodes.get("W"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("SW"))) {
			if (isNodeNotWall(childNodes.get("SW")) && isNodeNotWall(childNodes.get("SSW"))
					&& isNodeNotWall(childNodes.get("SS"))
					&& (isNodeNotWall(childNodes.get("W")) || isNodeNotWall(childNodes.get("SSE")))) {
				// if (currentTile.getSouthWest().boxContains(goal)) {
				openNodes.add(childNodes.get("SW"));
				closedNodes.add(childNodes.get("SW"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("S"))) {
			if (isNodeNotWall(childNodes.get("SS")) && isNodeNotWall(childNodes.get("SSE"))) {
				// if (currentTile.getSouth().boxContains(goal)) {
				openNodes.add(childNodes.get("S"));
				closedNodes.add(childNodes.get("S"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("SE"))) {
			if (isNodeNotWall(childNodes.get("SSE")) && isNodeNotWall(childNodes.get("SSEE"))
					&& isNodeNotWall(childNodes.get("SEE"))
					&& (isNodeNotWall(childNodes.get("EE")) || isNodeNotWall(childNodes.get("SS")))) {
				// if (currentTile.getSouthEast().boxContains(goal)) {
				openNodes.add(childNodes.get("SE"));
				closedNodes.add(childNodes.get("SE"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("E"))) {
			if (isNodeNotWall(childNodes.get("EE")) && isNodeNotWall(childNodes.get("SEE"))) {
				// if (currentTile.getEast().boxContains(goal)) {
				openNodes.add(childNodes.get("E"));
				closedNodes.add(childNodes.get("E"));
				// }
			}
		}
		if (!closedNodes.contains(childNodes.get("NE"))) {
			if (isNodeNotWall(childNodes.get("NE")) && isNodeNotWall(childNodes.get("NEE"))
					&& isNodeNotWall(childNodes.get("EE"))
					&& (isNodeNotWall(childNodes.get("SEE")) || isNodeNotWall(childNodes.get("N")))) {
				// if (currentTile.getNorthEast().boxContains(goal)) {
				openNodes.add(childNodes.get("NE"));
				closedNodes.add(childNodes.get("NE"));
				// }
			}
		}
	}

	private Vector2 findPathStart(Node currentNode) {
		Node node = currentNode;
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
		}
	}

	public Vector2 findPath() {
		initData();

		int searchedNodes = 0;
		while (!openNodes.isEmpty()) {
			searchedNodes++;
			Node currentNode = openNodes.poll(); // Tile current being checked

			if (currentNode.equals(goalNode)) { // if goal is reached
				return findPathStart(currentNode);
			}

			generateChildNodes(currentNode);
			addUnobstructedChildNodes();
		}

		Logger.warn("cannot find player");
		Logger.debug(searchedNodes, Logger.Category.AI);
		return new Vector2(0, 0);
	}

	private boolean isNodeNotWall(Node node) {
		return !walls.containsKey(node.getPosition());
	}
}

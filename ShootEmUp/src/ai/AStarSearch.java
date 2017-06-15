package ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import level.LevelMap;
import main.Logger;
import math.Vector2;
import object.Entity;

public class AStarSearch {

	private static Map<Vector2, Entity> walls;
	private static GoalBounder goalBounder;
	private Queue<AStarNode> openNodes;
	private Set<AStarNode> closedNodes;
	private AStarNode startNode;
	private Map<String, AStarNode> childNodes;
	private AStarNode goalNode;

	public AStarSearch(LevelMap map) {
		if (walls == null) {
			walls = map.getWalls();
		}
		if (goalBounder == null) {
			goalBounder = map.getGoalBounder();
		}
	}

	private void initData(Vector2 goal, Vector2 start) {

		startNode = new AStarNode(start);
		goalNode = new AStarNode(goal);
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

		childNodes.put("N", new AStarNode(new Vector2(currentX, currentY - 1), currentNode, goalNode.getPosition()));
		childNodes.put("NW", new AStarNode(new Vector2(currentX - 1, currentY - 1), currentNode, goalNode.getPosition()));
		childNodes.put("W", new AStarNode(new Vector2(currentX - 1, currentY), currentNode, goalNode.getPosition()));
		childNodes.put("SW", new AStarNode(new Vector2(currentX - 1, currentY + 1), currentNode, goalNode.getPosition()));
		childNodes.put("SSW", new AStarNode(new Vector2(currentX - 1, currentY + 2), currentNode, goalNode.getPosition()));
		childNodes.put("S", new AStarNode(new Vector2(currentX, currentY + 1), currentNode, goalNode.getPosition()));
		childNodes.put("SS", new AStarNode(new Vector2(currentX, currentY + 2), currentNode, goalNode.getPosition()));
		childNodes.put("SSE", new AStarNode(new Vector2(currentX + 1, currentY + 2), currentNode, goalNode.getPosition()));
		childNodes.put("SSEE", new AStarNode(new Vector2(currentX + 2, currentY + 2), currentNode, goalNode.getPosition()));
		childNodes.put("SE", new AStarNode(new Vector2(currentX + 1, currentY + 1), currentNode, goalNode.getPosition()));
		childNodes.put("SEE", new AStarNode(new Vector2(currentX + 2, currentY + 1), currentNode, goalNode.getPosition()));
		childNodes.put("EE", new AStarNode(new Vector2(currentX + 2, currentY), currentNode, goalNode.getPosition()));
		childNodes.put("NEE", new AStarNode(new Vector2(currentX + 2, currentY - 1), currentNode, goalNode.getPosition()));
		childNodes.put("E", new AStarNode(new Vector2(currentX + 1, currentY), currentNode, goalNode.getPosition()));
		childNodes.put("NE", new AStarNode(new Vector2(currentX + 1, currentY - 1), currentNode, goalNode.getPosition()));
	}

	private void addNode(BoundingBox box, AStarNode node) {
		if (box.boxContains(goalNode.getPosition())) {
			openNodes.add(node);
			closedNodes.add(node);
		}
	}

	private void addUnobstructedChildNodes(GoalboundingTile goalboundingTile) {
		if (!closedNodes.contains(childNodes.get("N"))) {
			if (isNodeNotWall(childNodes.get("N")) && isNodeNotWall(childNodes.get("NE"))) {
				addNode(goalboundingTile.getNorth(), childNodes.get("N"));
			}
		}
		if (!closedNodes.contains(childNodes.get("SSW"))) {
			if (isNodeNotWall(childNodes.get("SSW")) && isNodeNotWall(childNodes.get("N"))
					&& isNodeNotWall(childNodes.get("W"))
					&& ((isNodeNotWall(childNodes.get("SW")) || isNodeNotWall(childNodes.get("NE"))))) {
				if (goalboundingTile.getNorthWest().boxContains(goalNode.getPosition())) {
					addNode(goalboundingTile.getNorthWest(), childNodes.get("SSW"));
				}
			}
		}
		if (!closedNodes.contains(childNodes.get("W"))) {
			if (isNodeNotWall(childNodes.get("W")) && isNodeNotWall(childNodes.get("SW"))) {
				if (goalboundingTile.getWest().boxContains(goalNode.getPosition())) {
					addNode(goalboundingTile.getWest(), childNodes.get("W"));
				}
			}
		}
		if (!closedNodes.contains(childNodes.get("SW"))) {
			if (isNodeNotWall(childNodes.get("SW")) && isNodeNotWall(childNodes.get("SSW"))
					&& isNodeNotWall(childNodes.get("SS"))
					&& (isNodeNotWall(childNodes.get("W")) || isNodeNotWall(childNodes.get("SSE")))) {
				addNode(goalboundingTile.getSouthWest(), childNodes.get("SW"));
			}
		}
		if (!closedNodes.contains(childNodes.get("S"))) {
			if (isNodeNotWall(childNodes.get("SS")) && isNodeNotWall(childNodes.get("SSE"))) {
				addNode(goalboundingTile.getSouth(), childNodes.get("S"));
			}
		}
		if (!closedNodes.contains(childNodes.get("SE"))) {
			if (isNodeNotWall(childNodes.get("SSE")) && isNodeNotWall(childNodes.get("SSEE"))
					&& isNodeNotWall(childNodes.get("SEE"))
					&& (isNodeNotWall(childNodes.get("EE")) || isNodeNotWall(childNodes.get("SS")))) {
				addNode(goalboundingTile.getSouthEast(), childNodes.get("SE"));
			}
		}
		if (!closedNodes.contains(childNodes.get("E"))) {
			if (isNodeNotWall(childNodes.get("EE")) && isNodeNotWall(childNodes.get("SEE"))) {
				addNode(goalboundingTile.getEast(), childNodes.get("E"));
			}
		}
		if (!closedNodes.contains(childNodes.get("NE"))) {
			if (isNodeNotWall(childNodes.get("NE")) && isNodeNotWall(childNodes.get("NEE"))
					&& isNodeNotWall(childNodes.get("EE"))
					&& (isNodeNotWall(childNodes.get("SEE")) || isNodeNotWall(childNodes.get("N")))) {
				addNode(goalboundingTile.getNorthEast(), childNodes.get("NE"));
			}
		}
	}

	private Vector2 findPathStart(AStarNode currentNode) {
		AStarNode node = currentNode;
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

	public Vector2 findPath(Vector2 goal, Vector2 start) {
		initData(goal, start);

		int searchedNodes = 0;
		while (!openNodes.isEmpty()) {
			searchedNodes++;
			AStarNode currentNode = openNodes.poll(); // Tile current being checked

			if (currentNode.equals(goalNode)) { // if goal is reached
				return findPathStart(currentNode);
			}

			generateChildNodes(currentNode);
			GoalboundingTile goalboundingTile = goalBounder.getTile(currentNode.getPosition().x(),
					currentNode.getPosition().y());
			addUnobstructedChildNodes(goalboundingTile);
		}

		Logger.warn("cannot find player");
		Logger.debug(searchedNodes, Logger.Category.AI);
		return new Vector2(0, 0);
	}

	private boolean isNodeNotWall(AStarNode node) {
		return !walls.containsKey(node.getPosition());
	}
}

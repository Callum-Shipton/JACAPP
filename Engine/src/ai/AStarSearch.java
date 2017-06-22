package ai;

import java.util.HashMap;
import java.util.HashSet;
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
		AStarNode north = childNodes.get(key);
		if (!closedNodes.contains(north) && !GoalBounder.containsWall(north.getPosition(), north.getWidth(), walls)
				&& goalboundingTile.getBox(key).boxContains(goalNode.getPosition())) {
			openNodes.add(north);
			closedNodes.add(north);
		}
	}

	private void addUnobstructedChildNodes(Vector2 position) {
		GoalboundingTile goalboundingTile = goalBounder.getTile(position.x(), position.y(), width);

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
			AStarNode currentNode = openNodes.poll();

			if (containsGoal(currentNode, goalNode)) {
				return findPathStart(currentNode);
			}

			generateChildNodes(currentNode);

			addUnobstructedChildNodes(currentNode.getPosition());
		}

		Logger.warn("cannot find player");
		Logger.debug(searchedNodes, Logger.Category.AI);
		return new Vector2(0, 0);
	}

	private boolean containsGoal(AStarNode node, AStarNode goal) {

		Vector2 origin = node.getPosition();
		for (int i = (int) origin.x(); i < origin.x() + node.getWidth(); i++) {
			for (int j = (int) origin.y(); j < origin.y() + node.getWidth(); j++) {
				if (goal.getPosition().equals(new Vector2(i, j))) {
					return true;
				}
			}
		}
		return false;
	}

	public Vector2 getGridPosition(float x, float y) {
		return new Vector2((float) Math.floor(x / nodeWidth), (float) Math.floor(y / nodeWidth));
	}
}

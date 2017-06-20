package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

	private void addNode(BoundingBox box, AStarNode node) {
		if (box.boxContains(goalNode.getPosition())) {
			openNodes.add(node);
			closedNodes.add(node);
		}
	}

	private void addUnobstructedChildNodes(GoalboundingTile goalboundingTile) {
		AStarNode north = childNodes.get("N");
		if (!closedNodes.contains(north) && !containsWall(north)) {
				addNode(goalboundingTile.getNorth(), north);
		}
		AStarNode northWest = childNodes.get("NW");
		if (!closedNodes.contains(northWest) && !containsWall(northWest)) {
				addNode(goalboundingTile.getNorthWest(), northWest);
		}
		AStarNode west = childNodes.get("W");
		if (!closedNodes.contains(west) && !containsWall(west)) {
				addNode(goalboundingTile.getWest(), west);
		}
		AStarNode southWest = childNodes.get("SW");
		if (!closedNodes.contains(southWest) && !containsWall(southWest)) {
				addNode(goalboundingTile.getSouthWest(), southWest);
		}
		AStarNode south = childNodes.get("S");
		if (!closedNodes.contains(south) && !containsWall(south)) {
				addNode(goalboundingTile.getSouth(), south);
		}
		AStarNode southEast = childNodes.get("SE");
		if (!closedNodes.contains(southEast) && !containsWall(southEast)) {
				addNode(goalboundingTile.getSouthEast(), southEast);
		}
		AStarNode east = childNodes.get("E");
		if (!closedNodes.contains(east) && !containsWall(east)) {
				addNode(goalboundingTile.getEast(), east);
		}
		AStarNode northEast = childNodes.get("NE");
		if (!closedNodes.contains(northEast) && !containsWall(northEast)) {
				addNode(goalboundingTile.getNorthEast(), northEast);
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

			if (containsGoal(currentNode, goalNode)) { // if goal is reached
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

	private boolean containsWall(AStarNode node) {
		
		Vector2 origin = node.getPosition();
		for(int i = (int) origin.x(); i < origin.x() + node.getWidth(); i++){
			for(int j = (int) origin.y(); j < origin.y() + node.getWidth(); j++){
				if(walls.contains(new Vector2(i,j))){
					return true;
				}
			}
		}
		return false;
	}
	
private boolean containsGoal(AStarNode node, AStarNode goal) {
		
		Vector2 origin = node.getPosition();
		for(int i = (int) origin.x(); i < origin.x() + node.getWidth(); i++){
			for(int j = (int) origin.y(); j < origin.y() + node.getWidth(); j++){
				if(goal.getPosition().equals(new Vector2(i,j))){
					return true;
				}
			}
		}
		return false;
	}


	public Vector2 getGridPosition(float x, float y) {
		return new Vector2((float) Math.floor(x / nodeWidth),
				(float) Math.floor(y / nodeWidth));
	}
}

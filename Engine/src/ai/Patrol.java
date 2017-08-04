package ai;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.joml.Vector2i;

import ai.nodes.RangeNode;
import logging.Logger;

public class Patrol {

	private final Set<Vector2i> walls;
	private Queue<RangeNode> openNodes = new PriorityQueue<>();
	private List<RangeNode> closedNodes = new LinkedList<>();
	private List<RangeNode> edgeNodes = new LinkedList<>();
	private RangeNode startNode;
	private final int moveRange;
	private Vector2i patrolLoc;

	public Patrol(Set<Vector2i> walls, int moveRange) {
		this.walls = walls;
		this.moveRange = moveRange;
	}

	public Vector2i getTarget(Vector2i start) {
		if (patrolLoc == null || start.equals(patrolLoc)) {

			openNodes.clear();
			closedNodes.clear();
			edgeNodes.clear();
			RangeNode currentNode;

			startNode = new RangeNode(start);

			openNodes.add(startNode);

			while (!openNodes.isEmpty()) {
				currentNode = openNodes.poll();
				if (currentNode.getLayer() < moveRange) {
					addChildNodes(currentNode);
				} else {
					edgeNodes.add(currentNode);
				}

				closedNodes.add(currentNode);
			}

			int rand = new Random().nextInt(edgeNodes.size());
			patrolLoc = edgeNodes.get(rand).getPosition();

		}

		Logger.debug(patrolLoc.x + " " + patrolLoc.y, Logger.Category.AI);
		return patrolLoc;
	}

	public void addChildNodes(RangeNode node) {
		Set<RangeNode> childNodes = new HashSet<>();

		childNodes.add(node.getChild(0, -1));
		childNodes.add(node.getChild(-1, -1));
		childNodes.add(node.getChild(-1, 0));
		childNodes.add(node.getChild(-1, 1));
		childNodes.add(node.getChild(0, 1));
		childNodes.add(node.getChild(1, 1));
		childNodes.add(node.getChild(1, 0));
		childNodes.add(node.getChild(1, -1));

		for (RangeNode child : childNodes) {
			if (!closedNodes.contains(child) && !openNodes.contains(child) && !walls.contains(child.getPosition())) {
				openNodes.add(child);
			}
		}
	}
}

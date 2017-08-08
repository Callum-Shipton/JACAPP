package ai;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.joml.Vector2i;

import ai.nodes.Node;
import ai.nodes.RangeNode;
import loop.Loop;

public class Patrol {

	private final Set<Vector2i> walls;
	private Queue<RangeNode> openNodes = new PriorityQueue<>();
	private List<RangeNode> closedNodes = new LinkedList<>();
	private List<RangeNode> edgeNodes = new LinkedList<>();
	private final int moveRange;
	private Vector2i patrolLoc;
	private int size;

	private boolean patrolling = true;
	private int patrolCounter = 0;

	public Patrol(Set<Vector2i> walls, int moveRange, int size) {
		this.walls = walls;
		this.moveRange = moveRange;
		this.size = size;
	}

	public Vector2i getTarget(Vector2i start) {
		if (patrolLoc == null || start.equals(patrolLoc)) {

			openNodes.clear();
			closedNodes.clear();
			edgeNodes.clear();
			RangeNode currentNode;

			RangeNode startNode = new RangeNode(start, size);

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

		return patrolLoc;
	}

	public void addChildNodes(RangeNode node) {
		Map<String, RangeNode> childNodes = new HashMap<>();

		childNodes.put("N", node.getChild(0, -1));
		childNodes.put("NW", node.getChild(-1, -1));
		childNodes.put("W", node.getChild(-1, 0));
		childNodes.put("SW", node.getChild(-1, 1));
		childNodes.put("S", node.getChild(0, 1));
		childNodes.put("SE", node.getChild(1, 1));
		childNodes.put("E", node.getChild(1, 0));
		childNodes.put("NE", node.getChild(1, -1));

		for (Entry<String, RangeNode> child : childNodes.entrySet()) {
			RangeNode childNode = child.getValue();
			if (!closedNodes.contains(childNode) && !openNodes.contains(childNode)
					&& !Node.movesIntoWall(childNode, walls, child.getKey())) {
				openNodes.add(childNode);
			}
		}
	}

	public Vector2i update(Vector2i currentPos) {

		if (currentPos.equals(patrolLoc)) {
			if (patrolCounter > 0) {
				patrolCounter--;
				return currentPos;
			}

			if (patrolCounter <= 0) {
				patrolCounter = Loop.ticks(3);
			}
		}

		return getTarget(currentPos);
	}

	public void off() {
		patrolling = false;
	}

	public void on(Vector2i currentPos) {
		patrolCounter = Loop.ticks(3);
		patrolLoc = currentPos;
		patrolling = true;
	}

	public boolean isPatrolling() {
		return patrolling;
	}
}

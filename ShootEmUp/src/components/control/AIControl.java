package components.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import level.LevelMap;
import main.Logger;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class AIControl extends BaseControl {

	private static Map<Vector2, Entity> walls;
	private static GoalBounder goalBounder;
	private Queue<Node> openNodes;
	private Set<Node> closedNodes;
	private Node start;
	private Map<String, Node> childNodes;

	private BaseMovement BM;
	private BaseGraphics BG;
	private BaseAttack BA;
	private int counter = 0;
	private int aggression = 30;

	private Node goalNode;

	public AIControl(BaseGraphics BG, BaseAttack BA, BaseMovement BM) {
		this.BA = BA;
		this.BG = BG;
		this.BM = BM;
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
		start = new Node(Node.getGridPosition(BG.getX(), BG.getY()), null); // entity's
		// node
		childNodes = new HashMap<>();
		openNodes.add(start);
		closedNodes.add(start);
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
				if (node.getParent().equals(start)) {
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

	@Override
	public void receive(Message m, Entity e) {

	}

	private Vector2 calculateMovementVector(Vector2 target) {
		Vector2 movementVector = new Vector2(0.0f, 0.0f);
		float y = target.y() * LevelMap.getTileHeight();
		float x = target.x() * LevelMap.getTileWidth();

		if (y < BG.getY()) {
			if ((y - BG.getY()) > -BM.getSpeed()) {
				movementVector.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
			} else {
				movementVector.add(0.0f, -1.0f);
			}
		}
		if (x < BG.getX()) {
			if ((x - BG.getX()) > -BM.getSpeed()) {
				movementVector.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
			} else {
				movementVector.add(-1.0f, 0.0f);
			}
		}
		if (y > BG.getY()) {
			if ((y - BG.getY()) < BM.getSpeed()) {
				movementVector.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
			} else {
				movementVector.add(0.0f, 1.0f);
			}
		}
		if (x > BG.getX()) {
			if ((x - BG.getX()) < BM.getSpeed()) {
				movementVector.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
			} else {
				movementVector.add(1.0f, 0.0f);
			}
		}

		return movementVector;
	}

	private void attack(Entity e) {
		counter++;
		if (counter == aggression) {
			BA.attack(e, (BG instanceof AnimatedGraphics) ? ((AnimatedGraphics) BG).getDirection() : 0);
			counter = 0;
		}
	}

	@Override
	public void update(Entity e) {

		BaseGraphics PlayerGraphics = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		goalNode = new Node(Node.getGridPosition(PlayerGraphics.getX(), PlayerGraphics.getY()), null);
		Vector2 target = findPath();

		if (target != null) {
			Vector2 movementVector = calculateMovementVector(target);

			if (movementVector.length() > 0) {
				if (movementVector.length() > 1) {
					movementVector.normalize();
				}
				if (BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) BG).setAnimating(true);
				}
				BM.move(e, movementVector);
				if (BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) BG).setDirection((int) (Math.round(movementVector.Angle()) / 45));
				}

			} else if (BG instanceof AnimatedGraphics) {
				((AnimatedGraphics) BG).setAnimating(false);
			}
		}
		attack(e);
	}
}

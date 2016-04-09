package components.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import level.Map;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class AIControl extends BaseControl {

	private BaseMovement BM;
	private BaseGraphics BG;
	private BaseAttack BA;

	private int counter = 0;
	private int aggression = 30;
	private Vector2 target;
	private Vector2 goal;

	private static HashMap<Vector2, Entity> walls;
	private static GoalBounder goalBounder;

	public AIControl(BaseGraphics BG, BaseAttack BA, BaseMovement BM) {
		this.BA = BA;
		this.BG = BG;
		this.BM = BM;
		if (walls == null) {
			walls = ShootEmUp.getCurrentLevel().map.walls;
		}
		if (goalBounder == null) {
			goalBounder = ShootEmUp.getCurrentLevel().map.goalBounder;
		}
	}

	@Override
	public void update(Entity e) {
		BaseGraphics PlayerG = ShootEmUp.getCurrentLevel().getPlayer().getComponent(TypeComponent.GRAPHICS);
		goal = new Vector2((float) Math.floor(PlayerG.getX() / Map.TILE_WIDTH),
				(float) Math.floor(PlayerG.getY() / Map.TILE_HEIGHT));
		target = ai();
		float y = target.y() * Map.TILE_HEIGHT;
		float x = target.x() * Map.TILE_WIDTH;

		if (target != null) {
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < BG.getY()) {
				if ((y - BG.getY()) > -BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < BG.getX()) {
				if ((x - BG.getX()) > -BM.getSpeed()) {
					movement.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > BG.getY()) {
				if ((y - BG.getY()) < BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > BG.getX()) {
				if ((x - BG.getX()) < BM.getSpeed()) {
					movement.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1) {
					movement.normalize();
				}
				if (BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) BG).setAnimating(true);
				}
				BM.move(e, movement);
				if (BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) BG).setDirection((int) (Math.round(movement.Angle()) / 45));
				}
			} else if (BG instanceof AnimatedGraphics) {
				((AnimatedGraphics) BG).setAnimating(false);
			}
		}
		counter++;
		if (counter == aggression) {
			BA.attack(e, (BG instanceof AnimatedGraphics) ? ((AnimatedGraphics) BG).getDirection() : 0);
			counter = 0;
		}
	}

	public Vector2 ai() {
		PriorityQueue<Node> open = new PriorityQueue<Node>(); // queue for tiles
		HashSet<Node> closed = new HashSet<Node>(); // list of already viewed
		Node start = new Node(new Vector2((float) Math.floor(BG.getX() / Map.TILE_WIDTH),
				(float) Math.floor(BG.getY() / Map.TILE_HEIGHT)), null);

		open.add(start);
		closed.add(start);
		int nodes = 0;
		while (open.size() > 0) {
			nodes++;
			Node current = open.poll(); // Tile current being checked
			if (current.equals(goal)) { // if goal is reached
				Node node = current;
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

			float currentX = current.getPosition().x();
			float currentY = current.getPosition().y();

			// add children
			Node N = new Node(new Vector2(currentX, currentY - 1), current);
			Node NW = new Node(new Vector2(currentX - 1, currentY - 1), current);
			Node W = new Node(new Vector2(currentX - 1, currentY), current);
			Node SW = new Node(new Vector2(currentX - 1, currentY + 1), current);
			Node SSW = new Node(new Vector2(currentX - 1, currentY + 2), current);
			Node S = new Node(new Vector2(currentX, currentY + 1), current);
			Node SS = new Node(new Vector2(currentX, currentY + 2), current);
			Node SSE = new Node(new Vector2(currentX + 1, currentY + 2), current);
			Node SSEE = new Node(new Vector2(currentX + 2, currentY + 2), current);
			Node SE = new Node(new Vector2(currentX + 1, currentY + 1), current);
			Node SEE = new Node(new Vector2(currentX + 2, currentY + 1), current);
			Node EE = new Node(new Vector2(currentX + 2, currentY), current);
			Node NEE = new Node(new Vector2(currentX + 2, currentY - 1), current);
			Node E = new Node(new Vector2(currentX + 1, currentY), current);
			Node NE = new Node(new Vector2(currentX + 1, currentY - 1), current);

			Tile currentTile = goalBounder.getTile(currentX, currentY);

			if (!closed.contains(N)) {
				if (getWall(N) && getWall(NE)) {
					// if (currentTile.getNorth().boxContains(goal)) {
					open.add(N);
					closed.add(N);
					// }
				}
			}
			if (!closed.contains(NW)) {
				if (getWall(NW) && getWall(N) && getWall(W) && ((getWall(SW) || getWall(NE)))) {
					// if (currentTile.getNorthWest().boxContains(goal)) {
					open.add(NW);
					closed.add(NW);
					// }
				}
			}
			if (!closed.contains(W)) {
				if (getWall(W) && getWall(SW)) {
					// if (currentTile.getWest().boxContains(goal)) {
					open.add(W);
					closed.add(W);
					// }
				}
			}
			if (!closed.contains(SW)) {
				if (getWall(SW) && getWall(SSW) && getWall(SS) && (getWall(W) || getWall(SSE))) {
					// if (currentTile.getSouthWest().boxContains(goal)) {
					open.add(SW);
					closed.add(SW);
					// }
				}
			}
			if (!closed.contains(S)) {
				if (getWall(SS) && getWall(SSE)) {
					// if (currentTile.getSouth().boxContains(goal)) {
					open.add(S);
					closed.add(S);
					// }
				}
			}
			if (!closed.contains(SE)) {
				if (getWall(SSE) && getWall(SSEE) && getWall(SEE) && (getWall(EE) || getWall(SS))) {
					// if (currentTile.getSouthEast().boxContains(goal)) {
					open.add(SE);
					closed.add(SE);
					// }
				}
			}
			if (!closed.contains(E)) {
				if (getWall(EE) && getWall(SEE)) {
					// if (currentTile.getEast().boxContains(goal)) {
					open.add(E);
					closed.add(E);
					// }
				}
			}
			if (!closed.contains(NE)) {
				if (getWall(NE) && getWall(NEE) && getWall(EE) && (getWall(SEE) || getWall(N))) {
					// if (currentTile.getNorthEast().boxContains(goal)) {
					open.add(NE);
					closed.add(NE);
					// }
				}
			}
		}
		System.out.println("cannot find player");
		System.out.println(nodes);
		return new Vector2(0, 0);
	}

	@Override
	public void receive(Message m, Entity e) {

	}

	private boolean getWall(Node node) {
		return !walls.containsKey(node.getPosition());
	}
}

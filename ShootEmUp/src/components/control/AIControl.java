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

	private static HashMap<Vector2, Entity> walls;
	private static GoalBounder goalBounder;
	private BaseMovement BM;

	private BaseGraphics BG;
	private BaseAttack BA;
	private int counter = 0;
	private int aggression = 30;

	private Vector2 target;
	private Vector2 goal;

	public AIControl(BaseGraphics BG, BaseAttack BA, BaseMovement BM) {
		this.BA = BA;
		this.BG = BG;
		this.BM = BM;
		Map map = ShootEmUp.getCurrentLevel().getMap();
		if (walls == null) {
			walls = map.getWalls();
		}
		if (goalBounder == null) {
			goalBounder = map.getGoalBounder();
		}
	}

	public Vector2 ai() {
		PriorityQueue<Node> open = new PriorityQueue<Node>(); // queue for tiles
		HashSet<Node> closed = new HashSet<Node>(); // list of already viewed
		Node start = new Node(new Vector2((float) Math.floor(this.BG.getX() / Map.getTileWidth()),
				(float) Math.floor(this.BG.getY() / Map.getTileHeight())), null);

		open.add(start);
		closed.add(start);
		int nodes = 0;
		while (open.size() > 0) {
			nodes++;
			Node current = open.poll(); // Tile current being checked
			if (current.equals(this.goal)) { // if goal is reached
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

			goalBounder.getTile(currentX, currentY);

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

	private boolean getWall(Node node) {
		return !walls.containsKey(node.getPosition());
	}

	@Override
	public void receive(Message m, Entity e) {

	}

	@Override
	public void update(Entity e) {
		BaseGraphics PlayerG = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		this.goal = new Vector2((float) Math.floor(PlayerG.getX() / Map.getTileWidth()),
				(float) Math.floor(PlayerG.getY() / Map.getTileHeight()));
		this.target = ai();
		float y = this.target.y() * Map.getTileHeight();
		float x = this.target.x() * Map.getTileWidth();

		if (this.target != null) {
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < this.BG.getY()) {
				if ((y - this.BG.getY()) > -this.BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / this.BM.getSpeed()) * (y - this.BG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < this.BG.getX()) {
				if ((x - this.BG.getX()) > -this.BM.getSpeed()) {
					movement.add(((1.0f / this.BM.getSpeed()) * (x - this.BG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > this.BG.getY()) {
				if ((y - this.BG.getY()) < this.BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / this.BM.getSpeed()) * (y - this.BG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > this.BG.getX()) {
				if ((x - this.BG.getX()) < this.BM.getSpeed()) {
					movement.add(((1.0f / this.BM.getSpeed()) * (x - this.BG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1) {
					movement.normalize();
				}
				if (this.BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) this.BG).setAnimating(true);
				}
				this.BM.move(e, movement);
				if (this.BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) this.BG).setDirection((int) (Math.round(movement.Angle()) / 45));
				}
			} else if (this.BG instanceof AnimatedGraphics) {
				((AnimatedGraphics) this.BG).setAnimating(false);
			}
		}
		this.counter++;
		if (this.counter == this.aggression) {
			this.BA.attack(e, (this.BG instanceof AnimatedGraphics) ? ((AnimatedGraphics) this.BG).getDirection() : 0);
			this.counter = 0;
		}
	}
}

package components.control;

import AI.AStarSearch;
import AI.Node;
import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import level.LevelMap;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class AIControl extends BaseControl {

	private BaseMovement BM;
	private BaseGraphics BG;
	private BaseAttack BA;
	private int counter = 0;
	private int aggression = 30;

	public AIControl(BaseGraphics BG, BaseAttack BA, BaseMovement BM) {
		this.BA = BA;
		this.BG = BG;
		this.BM = BM;
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
		Node goalNode = new Node(Node.getGridPosition(PlayerGraphics.getX(), PlayerGraphics.getY()), null);
		Node startNode = new Node(Node.getGridPosition(BG.getX(), BG.getY()), null);
		AStarSearch search = new AStarSearch(goalNode, startNode);
		Vector2 target = search.findPath();

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

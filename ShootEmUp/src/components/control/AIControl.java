package components.control;

import ai.AStarSearch;
import ai.Node;
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

	private BaseMovement movement;
	private BaseGraphics graphics;
	private BaseAttack attack;
	private int counter = 0;
	private int aggression = 30;

	public AIControl(BaseGraphics graphics, BaseAttack attack, BaseMovement movement) {
		this.graphics = graphics;
		this.attack = attack;
		this.movement = movement;
	}

	@Override
	public void receive(Message m, Entity e) {

	}

	private Vector2 calculateMovementVector(Vector2 target) {
		Vector2 movementVector = new Vector2(0.0f, 0.0f);
		float targetY = target.y() * LevelMap.getTileHeight();
		float targetX = target.x() * LevelMap.getTileWidth();

		float x = graphics.getX();
		float y = graphics.getY();
		int speed = movement.getSpeed();

		if (targetY < y) {
			if ((targetY - y) > -speed) {
				movementVector.add(0.0f, getDifference(speed, targetY, y));
			} else {
				movementVector.add(0.0f, -1.0f);
			}
		} else if (targetY > y) {
			if ((targetY - y) < speed) {
				movementVector.add(0.0f, getDifference(speed, targetY, y));
			} else {
				movementVector.add(0.0f, 1.0f);
			}
		}
		if (targetX < x) {
			if ((targetX - x) > -speed) {
				movementVector.add(getDifference(speed, targetX, x), 0.0f);
			} else {
				movementVector.add(-1.0f, 0.0f);
			}
		} else if (targetX > x) {
			if ((targetX - x) < speed) {
				movementVector.add(getDifference(speed, targetX, x), 0.0f);
			} else {
				movementVector.add(1.0f, 0.0f);
			}
		}

		return movementVector;
	}

	private float getDifference(float speed, float pos1, float pos2) {
		return (1.0f / speed) * (pos1 - pos2);
	}

	private void attack(Entity e) {
		counter++;
		if (counter == aggression) {
			attack.attack(e, (graphics instanceof AnimatedGraphics) ? ((AnimatedGraphics) graphics).getDirection() : 0);
			counter = 0;
		}
	}

	@Override
	public void update(Entity e) {

		BaseGraphics playerGraphics = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		Node goalNode = new Node(Node.getGridPosition(playerGraphics.getX(), playerGraphics.getY()), null);
		Node startNode = new Node(Node.getGridPosition(graphics.getX(), graphics.getY()), null);
		AStarSearch search = new AStarSearch();
		Vector2 target = search.findPath(goalNode, startNode);

		if (target != null) {
			Vector2 movementVector = calculateMovementVector(target);

			if (movementVector.length() > 0) {
				if (movementVector.length() > 1) {
					movementVector.normalize();
				}
				if (graphics instanceof AnimatedGraphics) {
					((AnimatedGraphics) graphics).setAnimating(true);
				}
				movement.move(e, movementVector);
				if (graphics instanceof AnimatedGraphics) {
					((AnimatedGraphics) graphics).setDirection((int) (Math.round(movementVector.Angle()) / 45));
				}

			} else if (graphics instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphics).setAnimating(false);
			}
		}
		attack(e);
	}
}

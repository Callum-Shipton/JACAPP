package components.control;

import ai.AStarSearch;
import ai.Node;
import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
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
			Vector2 movementVector = calculateMovementVector(target, graphics.getX(), graphics.getY(),
					movement.getSpeed());

			if (movementVector.length() > 0) {
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

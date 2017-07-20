package components.control;

import org.joml.Vector2f;
import org.joml.Vector2i;

import ai.AStarSearch;
import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import entity.Entity;
import level.LevelMap;
import logging.Logger;
import logging.Logger.Category;
import main.ShootEmUp;
import math.VectorMath;

public class AIControl extends BaseControl {

	private transient int counter = 0;
	private transient int aggression = 30;
	private transient AStarSearch search;

	public AIControl() {

	}

	public AIControl(AIControl aiControl) {

	}

	@Override
	public void receive(Message m) {

	}

	private void attack(Entity e) {
		BaseGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		BaseAttack attackComponent = getEntity().getComponent(TypeComponent.ATTACK);
		counter++;
		if (counter == aggression) {
			attackComponent.attack(e, (graphicsComponent instanceof AnimatedGraphics)
					? ((AnimatedGraphics) graphicsComponent).getDirection() : 0);
			counter = 0;
		}
	}

	@Override
	public void update() {

		BaseGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
		BaseGraphics goalGraphics = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.GRAPHICS);

		if (search == null) {
			LevelMap map = ShootEmUp.getGame().getCurrentLevel().getMap();
			search = new AStarSearch(map.getWalls().keySet(), map.getGoalBounder(), LevelMap.TILE_WIDTH,
					(int) (graphicsComponent.getWidth() / LevelMap.TILE_WIDTH));
		}

		Vector2i goalVector = search.getGridPosition(goalGraphics.getX(), goalGraphics.getY());
		Vector2i startVector = search.getGridPosition(graphicsComponent.getX(), graphicsComponent.getY());
		Logger.debug("Entity: " + getEntity().getId() + " at Current Tile: " + startVector.x() + ", " + startVector.y(),
				Category.AI);

		Vector2i target = search.findPath(goalVector, startVector);

		Vector2f movementVector = calculateMovementVector(new Vector2f(target.x(), target.y()),
				graphicsComponent.getX(), graphicsComponent.getY(), movementComponent.getSpeed());

		if (movementVector.length() > 0) {
			if (graphicsComponent instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphicsComponent).setAnimating(true);
			}
			movementComponent.move(getEntity(), movementVector);
			if (graphicsComponent instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphicsComponent)
						.setDirection((int) (Math.round(VectorMath.angle(movementVector)) / 45));
			}

		} else if (graphicsComponent instanceof AnimatedGraphics) {
			((AnimatedGraphics) graphicsComponent).setAnimating(false);
		}

		attack(getEntity());
	}
}

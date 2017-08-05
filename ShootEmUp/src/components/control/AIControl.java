package components.control;

import org.joml.Vector2f;
import org.joml.Vector2i;

import ai.AStarSearch;
import ai.Patrol;
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
import loop.Loop;
import main.ShootEmUp;
import math.VectorMath;

public class AIControl extends BaseControl {

	private transient int attackCounter = 0;
	private transient int aggression = 30;
	private transient AStarSearch search;
	private transient Patrol patrol;

	private Vector2i enemyVector;
	private Vector2i patrolVector;
	private Vector2i nextNodeVector;
	private transient int patrolCounter = 0;

	private boolean research = true;

	public AIControl(AIControl aiControl) {

	}

	@Override
	public void receive(Message m) {

	}

	private void attack(Entity e) {
		BaseGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		BaseAttack attackComponent = getEntity().getComponent(TypeComponent.ATTACK);
		attackCounter++;
		if (attackCounter == aggression) {
			attackComponent.attack(e, (graphicsComponent instanceof AnimatedGraphics)
					? ((AnimatedGraphics) graphicsComponent).getDirection() : 0);
			attackCounter = 0;
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
			patrol = new Patrol(map.getWalls().keySet(), 6);
		}
		
		Vector2i startVector = search.getGridPosition(graphicsComponent.getX(), graphicsComponent.getY());

		Vector2i newVector = search.getGridPosition(goalGraphics.getX(), goalGraphics.getY());
		if (!newVector.equals(enemyVector) || !startVector.equals(nextNodeVector)) {
			enemyVector = newVector;
			research = true;
		}
		
		if(patrolCounter > 0) {
			patrolCounter--;
		}
		
		if(patrolCounter <= 0) {
			if (startVector.equals(patrolVector)) {
				patrolCounter = Loop.ticks(3);
			}
			patrolVector = patrol.getTarget(startVector);
			nextNodeVector = patrolVector;
		}
		
		if (research) {
			if (inRange(startVector, enemyVector)) {
				nextNodeVector = enemyVector;
			}
			Logger.debug(
					"Entity: " + getEntity().getId() + " at Current Tile: " + startVector.x() + ", " + startVector.y(),
					Category.AI);

			
			research = false;
		}
		
		nextNodeVector = search.findPath(startVector, nextNodeVector);

		Vector2f movementVector = calculateMovementVector(new Vector2f(nextNodeVector.x(), nextNodeVector.y()),
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

	private boolean inRange(Vector2i start, Vector2i goal) {
		return false;
	}
}

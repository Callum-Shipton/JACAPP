package components.control;

import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector2i;

import ai.AStarSearch;
import ai.Patrol;
import ai.nodes.RangeSearch;
import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import entity.Entity;
import level.LevelMap;
import main.ShootEmUp;
import math.VectorMath;

public class AIControl extends BaseControl {

	private int attackCounter = 0;
	private int aggression = 30;
	private AStarSearch search;
	private Patrol patrol;
	private RangeSearch rangeSearch;

	private Vector2i enemyVector;
	private Vector2i startVector;
	private Vector2i targetVector;

	private static final int PATROL_DISTANCE = 6;
	private static final double VIEW_RANGE = 10;

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

		LevelMap map = ShootEmUp.getGame().getCurrentLevel().getMap();
		Set<Vector2i> wallLocs = map.getWalls().keySet();
		int width = (int) (graphicsComponent.getWidth() / LevelMap.TILE_WIDTH);

		if (search == null) {
			search = new AStarSearch(wallLocs, map.getGoalBounder(), LevelMap.TILE_WIDTH, width);
			patrol = new Patrol(wallLocs, PATROL_DISTANCE, width);
			rangeSearch = new RangeSearch(wallLocs, VIEW_RANGE);
			// rangeSearch.setOn(false);
		}

		Vector2i currentStartVector = search.getGridPosition(graphicsComponent.getX(), graphicsComponent.getY());
		Vector2i currentEnemyVector = search.getGridPosition(goalGraphics.getX(), goalGraphics.getY());
		Vector2i currentTargetVector = targetVector;

		if(currentTargetVector == null) {
			currentTargetVector = currentStartVector;
		}
		
		if (!currentStartVector.equals(startVector) || !currentEnemyVector.equals(enemyVector)) {
			if (rangeSearch.enemyInRange(currentStartVector, currentEnemyVector)) {
				currentTargetVector = currentEnemyVector;
			}
		}
		
		
		if (!currentTargetVector.equals(currentEnemyVector)){
			currentTargetVector = patrol.update(currentStartVector);
		}

		Vector2i nextNode = currentTargetVector;

		if (!nextNode.equals(currentStartVector)) {
			nextNode = search.findPath(currentStartVector, currentTargetVector);
		}

		enemyVector = currentEnemyVector;
		startVector = currentStartVector;
		targetVector = currentTargetVector;

		Vector2f movementVector = calculateMovementVector(new Vector2f(nextNode.x(), nextNode.y()),
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

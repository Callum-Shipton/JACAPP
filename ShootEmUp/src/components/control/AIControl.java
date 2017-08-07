package components.control;

import java.util.HashSet;
import java.util.Set;

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

	private int attackCounter = 0;
	private int aggression = 30;
	private AStarSearch search;
	private Patrol patrol;

	private Vector2i enemyVector;
	private Vector2i enemyLastSeenVector;
	private Vector2i patrolVector;
	private Vector2i nextNodeVector;
	private int patrolCounter = 0;

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

		LevelMap map = ShootEmUp.getGame().getCurrentLevel().getMap();
		Set<Vector2i> wallLocs = map.getWalls().keySet();
		int width = (int) (graphicsComponent.getWidth() / LevelMap.TILE_WIDTH);

		if (search == null) {
			search = new AStarSearch(wallLocs, map.getGoalBounder(), LevelMap.TILE_WIDTH, width);
			patrol = new Patrol(wallLocs, 6);
		}

		Vector2i startVector = search.getGridPosition(graphicsComponent.getX(), graphicsComponent.getY());

		Vector2i newVector = search.getGridPosition(goalGraphics.getX(), goalGraphics.getY());
		if (!newVector.equals(enemyVector) || !startVector.equals(nextNodeVector)) {
			enemyVector = newVector;
			research = true;
		}

		if (patrolCounter > 0) {
			patrolCounter--;
		}

		if (patrolCounter <= 0) {
			if (startVector.equals(patrolVector)) {
				patrolCounter = Loop.ticks(3);
			}
			patrolVector = patrol.getTarget(startVector, width);
			nextNodeVector = patrolVector;
		}

		if (research) {
			if (inRange(startVector, enemyVector, wallLocs)) {
				nextNodeVector = enemyVector;
				enemyLastSeenVector = enemyVector;
			} else if (enemyLastSeenVector != null) {
				nextNodeVector = enemyLastSeenVector;
			}
			Logger.debug(
					"Entity: " + getEntity().getId() + " at Current Tile: " + startVector.x() + ", " + startVector.y(),
					Category.AI);

			research = false;
		}

		Vector2i nextNode = search.findPath(startVector, nextNodeVector);

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

	private static boolean inRange(Vector2i start, Vector2i goal, Set<Vector2i> walls) {

		final double viewRange = 10;
		if (start.distance(goal) < viewRange) {
			Set<Vector2i> viewTiles = getTilesOnLine(start, goal);
			for (Vector2i tile : viewTiles) {
				if (walls.contains(tile)) {
					return false;
				}
			}
			return true;
		}

		return true;
	}

	private static Set<Vector2i> getTilesOnLine(Vector2i start, Vector2i goal) {
		Set<Vector2i> tiles = new HashSet<>();
		int x0 = start.x();
		int y0 = start.y();
		int x1 = goal.x();
		int y1 = goal.y();

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while (true) {
			tiles.add(new Vector2i(x0, y0));

			if (x0 == x1 && y0 == y1)
				break;

			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}

		return tiles;
	}
}

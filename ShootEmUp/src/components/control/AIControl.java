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

public class AIControl extends BaseControl {

	private BaseMovement movement;
	private BaseAttack attack;
	private int counter = 0;
	
	private int aggression = 30;
	private AStarSearch search;
	private BaseGraphics playerGraphics;

	public AIControl(BaseGraphics graphics, BaseAttack attack, BaseMovement movement) {
		this.graphics = graphics;
		this.attack = attack;
		this.movement = movement;

		LevelMap map = ShootEmUp.getGame().getCurrentLevel().getMap();
		playerGraphics = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.GRAPHICS);
		search = new AStarSearch(map.getWalls().keySet(), map.getGoalBounder(), LevelMap.TILE_WIDTH,
				(int) (graphics.getWidth() / LevelMap.TILE_WIDTH));
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

		Vector2i goalVector = search.getGridPosition(playerGraphics.getX(), playerGraphics.getY());
		Vector2i startVector = search.getGridPosition(graphics.getX(), graphics.getY());
		Logger.debug("Entity: " + e.getId() +  " at Current Tile: " + startVector.x() + ", " + startVector.y(), Category.AI);

		Vector2i target = search.findPath(goalVector, startVector);

		Vector2f movementVector = calculateMovementVector(new Vector2f(target.x(), target.y()), graphics.getX(), graphics.getY(), movement.getSpeed());

		if (movementVector.length() > 0) {
			if (graphics instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphics).setAnimating(true);
			}
			movement.move(e, movementVector);
			if (graphics instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphics).setDirection((int) (Math.round(movementVector.angle()) / 45));
			}

		} else if (graphics instanceof AnimatedGraphics) {
			((AnimatedGraphics) graphics).setAnimating(false);
		}

		attack(e);
	}
}

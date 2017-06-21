package components.control;

import ai.AStarSearch;
import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import entity.Entity;
import level.LevelMap;
import main.ShootEmUp;
import math.Vector2;

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
		LevelMap map = ShootEmUp.getCurrentLevel().getMap();
		playerGraphics = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		search = new AStarSearch(map.getWalls().keySet(), map.getGoalBounder(), graphics.getWidth() / 2,
				(int) (graphics.getWidth() / LevelMap.getTileWidth()));
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

		Vector2 goalVector = search.getGridPosition(playerGraphics.getX(), playerGraphics.getY());
		Vector2 startVector = search.getGridPosition(graphics.getX(), graphics.getY());
		Vector2 target = search.findPath(goalVector, startVector);

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

package components.control;

import components.Component;
import components.TypeComponent;
import components.graphical.BaseGraphics;
import level.LevelMap;
import math.Vector2;
import object.Entity;

public abstract class BaseControl extends Component implements ControlComponent {

	protected TypeComponent type = TypeComponent.CONTROL;
	protected BaseGraphics graphics;

	private static float getDifference(float speed, float pos1, float pos2) {
		return (1.0f / speed) * (pos1 - pos2);
	}

	protected Vector2 calculateMovementVector(Vector2 target, float x, float y, int speed) {
		Vector2 movementVector = new Vector2(0.0f, 0.0f);
		float targetY = target.y() * LevelMap.getTileHeight();
		float targetX = target.x() * LevelMap.getTileWidth();

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

		if (movementVector.length() > 1) {
			movementVector.normalize();
		}

		return movementVector;
	}

	@Override
	public void destroy(Entity e) {

	}

	@Override
	public TypeComponent getType() {
		return this.type;
	}

}

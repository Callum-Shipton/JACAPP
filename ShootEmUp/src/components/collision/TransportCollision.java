package components.collision;

import entity.Entity;
import level.Level;
import main.ShootEmUp;

public class TransportCollision extends BaseCollision {

	private final boolean direction;

	public TransportCollision(int direction) {
		this.direction = (direction == 1) ? true : false;
		moveBack = false;
	}

	@Override
	public void collision(Entity e, Entity hit) {
		if (hit.equals(ShootEmUp.getGame().getPlayer())) {
			Level l = ShootEmUp.getGame().getCurrentLevel();
			if (direction) {
				l.setLevelStateNext();
			} else {
				l.setLevelStatePrev();
			}
		}
	}

	@Override
	public void update() {
	}

}

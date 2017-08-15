package components.collision;

import entity.Entity;
import level.Level;
import main.ShootEmUp;
import maze.Direction;

public class TransportCollision extends BaseCollision {

	private final Direction direction;

	public TransportCollision(Direction direction) {
		this.direction = direction;
		moveBack = false;
	}

	@Override
	public void collision(Entity e, Entity hit) {
		if (hit.equals(ShootEmUp.getGame().getPlayer())) {
			Level l = ShootEmUp.getGame().getCurrentLevel();
			l.setLevelState(direction);
		}
	}

	@Override
	public void update() {
	}

}

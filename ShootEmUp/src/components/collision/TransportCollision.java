package components.collision;

import entity.Entity;
import level.Level;
import main.ShootEmUp;

public class TransportCollision extends BaseCollision {

	public TransportCollision() {
	}

	@Override
	public void collision(Entity e, Entity hit) {
		if(hit.equals(ShootEmUp.getGame().getPlayer())){
			Level l = ShootEmUp.getGame().getCurrentLevel();
			l.setLevelFinished(true);
		}
	}

	@Override
	public void update() {
	}

}

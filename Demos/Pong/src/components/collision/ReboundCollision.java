package components.collision;

import components.TypeComponent;
import components.control.BallControl;
import entity.Entity;

public class ReboundCollision extends BaseCollision {


	public ReboundCollision() {
		moveBack = true;
	}

	public ReboundCollision(ReboundCollision reboundCollision) {
		this();
	}

	@Override
	public void collision(Entity e, Entity hit) {
		BallControl BC = e.getComponent(TypeComponent.CONTROL);
		BC.bounce();
	}

	@Override
	public void update() {
	}
}

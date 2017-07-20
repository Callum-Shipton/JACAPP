package components.control;

import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.movement.BaseMovement;
import entity.Entity;
import level.LevelMap;

public class RangeControl extends LineControl {

	private int range;
	private boolean rangeSet = false;

	public RangeControl(int range) {
		this.range = range * (LevelMap.TILE_WIDTH);
	}

	@Override
	public void update(Entity e) {
		super.update(e);

		if (!rangeSet) {
			BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
			range = range / movementComponent.getSpeed();
		}

		range--;
		if (range <= 0) {
			e.destroy();
			e.send(new Message(MessageId.ENTITY_DIED));
		}
	}

}

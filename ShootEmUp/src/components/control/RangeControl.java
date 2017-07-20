package components.control;

import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.movement.BaseMovement;
import level.LevelMap;

public class RangeControl extends LineControl {

	private int range;
	private transient boolean rangeSet = false;

	public RangeControl(int range) {
		this.range = range * (LevelMap.TILE_WIDTH);
	}

	public RangeControl(RangeControl rangeControl) {
		this(rangeControl.range);
	}

	@Override
	public void update() {
		super.update();

		if (!rangeSet) {
			BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
			range = range / movementComponent.getSpeed();
		}

		range--;
		if (range <= 0) {
			getEntity().destroy();
			getEntity().send(new Message(MessageId.ENTITY_DIED));
		}
	}

}

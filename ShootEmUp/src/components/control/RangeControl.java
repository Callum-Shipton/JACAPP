package components.control;

import components.Message;
import components.MessageId;
import entity.Entity;
import level.LevelMap;

public class RangeControl extends LineControl {

	private int range;

	public RangeControl(int range, Entity entity) {
		super(entity);
		this.range = (range * (LevelMap.TILE_WIDTH)) / movementComponent.getSpeed();
	}

	@Override
	public void update(Entity e) {
		super.update(e);
		range--;
		if (range <= 0) {
			e.destroy();
			e.send(new Message(MessageId.ENTITY_DIED));
		}
	}

}

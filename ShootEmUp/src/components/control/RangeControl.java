package components.control;

import components.Message;
import components.MessageId;
import components.graphical.AnimatedGraphics;
import components.movement.BaseMovement;
import entity.Entity;
import level.LevelMap;

public class RangeControl extends LineControl {

	private int range;

	public RangeControl(AnimatedGraphics AG, BaseMovement BM, int range) {
		super(AG, BM);
		this.range = (range * (LevelMap.TILE_WIDTH)) / BM.getSpeed();
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

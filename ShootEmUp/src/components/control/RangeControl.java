package components.control;

import components.Message;
import components.graphical.AnimatedGraphics;
import components.movement.BaseMovement;
import level.Map;
import object.Entity;

public class RangeControl extends LineControl {

	private int range;

	public RangeControl(AnimatedGraphics AG, BaseMovement BM, int range) {
		super(AG, BM);
		this.range = (range * (Map.TILE_WIDTH)) / BM.getSpeed();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Entity e) {
		super.update(e);
		range--;
		if (range <= 0) {
			e.destroy();
			e.send(Message.ENTITY_DIED);
		}
	}

}

package components.control;

import object.Entity;
import math.Vector2;
import components.Message;
import components.graphical.AnimatedGraphics;
import components.movement.BaseMovement;

public class LineControl extends BaseControl{
	
	private BaseMovement BM;
	private AnimatedGraphics AG;
	
	public LineControl(AnimatedGraphics AG, BaseMovement BM){
		this.AG = AG;
		this.BM = BM;
	}
	
	@Override
	public void update(Entity e) {
		int direction = AG.getDirection();
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (direction >= 1 && direction <= 3) {
			movement.add(1.0f, 0.0f);
		}
		if (direction >= 5) {
			movement.add(-1.0f, 0.0f);
		}
		if (direction <= 1 || direction >= 7) {
			movement.add(0.0f, -1.0f);
		}
		if (direction >= 3 && direction <= 5) {
			movement.add(0.0f, 1.0f);
		}
		movement.normalize();
		BM.move(e, movement);
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

}

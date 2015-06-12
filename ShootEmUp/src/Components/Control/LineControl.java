package Components.Control;

import Components.Message;
import Components.Graphical.AnimatedGraphics;
import Components.Movement.BaseMovement;
import Math.Vector2;
import Object.Entity;

public class LineControl extends BaseControl{
	
	private AnimatedGraphics AG;
	private BaseMovement BM;
	
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

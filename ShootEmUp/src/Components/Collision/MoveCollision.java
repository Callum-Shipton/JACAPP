package Components.Collision;

import Components.Message;
import Object.Entity;

public class MoveCollision extends BaseCollision {

	@Override
	public void collision(Entity hitter, Entity hit) {
		/*
		 * if (Math.abs(vec.x()) < speed) {
			e.setPosX(e.getPosX() + ((Math.round(moveVec.x())) - vec.x()
					- (moveVec.x() / Math.abs(moveVec.x()))));
		} else if(Math.abs(vec.x()) >= speed); 
		else if (Math.abs(vec.z()) < speed) {
			e.setPosX(e.getPosX() + ((Math.round(moveVec.x())) - vec.z()
					- (moveVec.x() / Math.abs(moveVec.x()))));
		}
		onCollide(hit); 
		*/
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}

}

package Components.Collision;

import Object.Entity;

public class MoveCollision extends BaseCollision {

	@Override
	public void Collision(Entity hitter, Entity hit) {
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

}

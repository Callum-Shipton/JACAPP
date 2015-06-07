package Components.Movement;

import Math.Vector4;
import Object.Entity;

public class FlyingMovement extends BasicMovement {
	
	public Vector4 doesCollide(Entity moving, Entity checked) {
		if(flat == true){
			return null;
		}
		return super.doesCollide(moving, checked);
	}
}

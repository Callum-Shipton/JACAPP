package Components.Movement;

import Math.Vector4;
import Object.Entity;

public class FlyingMovement {
	
	public Vector4 doesCollide(Entity e, float x, float y) {
		if(flat == true){
			return null;
		}
		return super.doesCollide(e, x, y);
	}
}

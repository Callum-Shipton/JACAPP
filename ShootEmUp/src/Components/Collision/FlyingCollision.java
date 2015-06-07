package Components.Collision;

import Math.Vector4;
import Object.Entity;

public class FlyingCollision extends GroundCollision{
	
	public Vector4 doesCollide(Entity e, float x, float y) {
		if(flat == true){
			return null;
		}
		return super.doesCollide(e, x, y);
	}
}

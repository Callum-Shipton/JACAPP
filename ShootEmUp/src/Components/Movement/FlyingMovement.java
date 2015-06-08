package Components.Movement;

import Components.Collision.BaseCollision;
import Components.Graphical.BaseGraphics;
import Math.Vector4;
import Object.Entity;

public class FlyingMovement extends BasicMovement {
	
	public FlyingMovement(Entity e, BaseCollision BC, BaseGraphics BG) {
		super(e, BC, BG);
	}

	public Vector4 doesCollide(Entity moving, Entity checked) {
		if(flat == true){
			return null;
		}
		return super.doesCollide(moving, checked);
	}
}

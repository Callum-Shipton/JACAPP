package Components.Physics;

import Math.Vector4;
import Object.Entity;

public class FlyingCollision extends GroundCollision{
	
	public Vector4 doesCollide(Entity e, float x, float y) {
		if(flat == true){
			return null;
		}
		
		if((Math.abs(e.getPosX() - x) > 74) && (Math.abs(e.getPosY() - y) > 74)){
			return null;
		}
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}
		
		x += e.getWidth()/4;

		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x += e.getWidth()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x += e.getWidth()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x += e.getWidth()/4;

		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y += e.getHeight()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y += e.getHeight()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y += e.getHeight()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y += e.getHeight()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x -= e.getWidth()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x -= e.getWidth()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x -= e.getWidth()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		x -= e.getWidth()/4;

		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y -= e.getHeight()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y -= e.getHeight()/4;
		
		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}

		y -= e.getHeight()/4;

		if (collideFunction(e, x, y) != null) {
			return collideFunction(e, x, y);
		}
		return null;
	}
}

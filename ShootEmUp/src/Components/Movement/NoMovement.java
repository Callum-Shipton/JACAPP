package Components.Movement;

import Math.Vector4;
import Object.Entity;

public class NoMovement extends BaseMovement{

	@Override
	public Vector4 doesCollide(Entity moving, Entity checked) {
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

	@Override
	public Vector4 collideFunction(Entity e, float x, float y) {
		if (((x >= e.getPosX()) && (x <= (e.getPosX() + e.getWidth()))) && ((y >= e.getPosY()) && (y <= (e.getPosY() + e.getHeight())))) {
			return new Vector4(x - e.getPosX(), y - e.getPosY(), x -(e.getPosX() + e.getWidth()), y - (e.getPosY() + e.getHeight()));	
		}
		return null;
	}
}

package Components.Movement;

import Components.Message;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public class NoMovement extends BaseMovement{

	@Override
	public Vector4 doesCollide(Entity moving, Entity checked) {
		if((Math.abs(checked.getPosX() - moving.getPosX()) > 74) && (Math.abs(moving.getPosY() - checked.getPosY()) > 74)){
			return null;
		}
		
		float x = moving.getPosX();
		float y = moving.getPosY();
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}
		
		x += checked.getWidth()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x += checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x += checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x += checked.getWidth()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y -= checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y -= checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y -= checked.getHeight()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
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

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Entity e, Vector2 moveVec) {
		// Do nothing
	}
}

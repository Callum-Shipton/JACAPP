package Object;

import Math.Vector4;

public class Collidable {
	protected float posX;
	protected float posY;
	protected float width;
	protected float height;
	protected boolean flat;

	public Collidable(float posX, float posY, float width, float height, boolean flat) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.flat = flat;
	}

	public Collidable(float posX, float posY, boolean flat) {
		this.posX = posX;
		this.posY = posY;
		this.flat = flat;
	}

	public Vector4 doesCollide(float x, float y, float w, float h) {
		if((Math.abs(posX - x) > 74) && (Math.abs(posY - y) > 74)){
			return null;
		}
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x += w/4;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x += w/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x += w/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x += w/4;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y += h/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y += h/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y += h/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y += h/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x -= w/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x -= w/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x -= w/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x -= w/4;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y -= h/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y -= h/4;
		
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y -= h/4;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}
		return null;
	}

	public Vector4 collideFunction(float x, float y) {
		if (((x >= posX) && (x <= (posX + width))) && ((y >= posY) && (y <= (posY + height)))) {
			return new Vector4(x - posX, y - posY, x -(posX + width), y - (posY + height));	
		}
		return null;
	}

	public void onCollide(Character hit) {
		return;
	}
}

package Object;

import Math.Vector2;

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

	public Vector2 doesCollide(float x, float y, float w, float h) {
		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x += w;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		y += h;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}

		x -= w;

		if (collideFunction(x, y) != null) {
			return collideFunction(x, y);
		}
		return null;
	}

	public Vector2 collideFunction(float x, float y) {
		if (((x >= posX) && (x <= (posX + width))) && ((y >= posY) && (y <= (posY + height)))) {
			return new Vector2(posX - x, posY - y);	
		}
		return null;
	}

	public void onCollide(NPC hit) {
		return;
	}
}

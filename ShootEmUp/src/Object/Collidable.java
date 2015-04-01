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

	public Vector4 doesCollide(float x, float y, float w, float h) {
		if (collideFunction(x, y, w, h, posX, posY) != null) {
			return collideFunction(x, y, w, h, posX, posY);
		}
		if (collideFunction(x, y, w, h, posX + width, posY) != null) {
			return collideFunction(x, y, w, h, posX + width, posY);
		}
		if (collideFunction(x, y, w, h, posX + width, posY + height) != null) {
			return collideFunction(x, y, w, h, posX + width, posY + height);
		}
		if (collideFunction(x, y, w, h, posX , posY + height) != null) {
			return collideFunction(x, y, w, h, posX, posY + height);
		}
		return null;
	}

	public Vector4 collideFunction(float x, float y, float w, float h, float X, float Y) { //float X and Y are the x and y of the current collidable
		if (((X >= x) && (X <= (x + w))) && ((Y >= y) && (Y <= (y + h)))) {
			return new Vector4(x - posX, y - posY, x -(posX + width), y - (posY + height));	
		}
		return null;
	}

	public void onCollide(NPC hit) {
		return;
	}
}

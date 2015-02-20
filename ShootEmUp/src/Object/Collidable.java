package Object;

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

	public float doesCollide(float x, float y, float w, float h) {
		if (collideFunction(x, y) != 0) {
			return collideFunction(x, y);
		}

		x += w;

		if (collideFunction(x, y) != 0) {
			return collideFunction(x, y);
		}

		y += h;

		if (collideFunction(x, y) != 0) {
			return collideFunction(x, y);
		}

		x -= w;

		if (collideFunction(x, y) != 0) {
			return collideFunction(x, y);
		}
		return 0;
	}

	public float collideFunction(float x, float y) {
		if (((x >= posX) && (x <= (posX + width))) && ((y >= posY) && (y <= (posY + height)))) {
			if(((x >= posX) && (x <= (posX + width)))){
				return (x - posX);
			} else {
				return (y - posY);
			}
				
		}
		return 0;
	}

	public void onCollide(NPC hit) {
		return;
	}
}

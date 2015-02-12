package Object;

public class Enemy extends Entity {

	public Enemy(int posX, int posY, int speed, int direction, int image) {
		super(posX, posY, speed, direction, image);
	}

	public void update() {
		posX--;
	}
}

package Object;

public abstract class NPC extends Entity {
	protected int health;
	
	public NPC(float x, float y, float width, float height, int speed, int direction, int image){
		super(x, y, width, height, speed, direction, image);
		health = 100;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
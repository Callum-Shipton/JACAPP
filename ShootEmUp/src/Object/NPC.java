package Object;

import Main.ShootEmUp;

public abstract class NPC extends Entity {
	protected int health;
	
	public NPC(float x, float y, float width, float height, int speed, int direction, int image){
		super(x, y, width, height, speed, direction, image);
		health = 100;
	}
	
	public void update(){
		
	}
	
	public void checkDead(){
		if(health <= 0){
			ShootEmUp.currentLevel.characters.remove(this);
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void damage(int damage) {
		this.health -= damage;
	}
}

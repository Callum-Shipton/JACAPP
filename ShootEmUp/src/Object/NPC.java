package Object;

import Main.ShootEmUp;
import Math.Vector2;

public abstract class NPC extends Entity {
	protected int health;
	
	public NPC(float x, float y, float width, float height, int speed, int direction, int image){
		super(x, y, width, height, speed, direction, image);
		health = 100;
	}
	
	public void update(){
		checkDead();
		Vector2 movement = new Vector2(0.0f,0.0f);
		if(ShootEmUp.currentLevel.getPlayer().getY() <= posY){
			movement.add(0.0f, -1.0f);
		}
		if(ShootEmUp.currentLevel.getPlayer().getX() <= posX){
			movement.add(-1.0f, 0.0f);
		}
		if(ShootEmUp.currentLevel.getPlayer().getY() >= posY){
			movement.add(0.0f, 1.0f);
		}
		if(ShootEmUp.currentLevel.getPlayer().getX() >= posX){
			movement.add(1.0f, 0.0f);
		}
		if(movement.length() > 0){
			if(movement.length() > 1) movement.normalize();
			move(movement);
		}
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

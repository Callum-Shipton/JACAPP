package Object;

import Main.ShootEmUp;
import Math.Vector2;


public class Particle extends Entity{
	private float distance = 0.0f;
	
	public Particle(float posX, float posY, float w, float h, int speed, int direction, int image){
		super(posX, posY, w, h, speed, direction, image);
		width = 32f;
		height = 32f;
	}
	
	public void update(){
		Vector2 movement = new Vector2(0.0f,0.0f);
		if(direction >= 1 && direction <= 3){
			movement.add(1.0f,0.0f);
		}
		if(direction >= 5){
			movement.add(-1.0f,0.0f);
		}
		if(direction <= 1 || direction >= 7){
			movement.add(0.0f,-1.0f);
		}
		if(direction >= 3 && direction <= 5){
			movement.add(0.0f,1.0f);
		}
		movement.normalize();
		move(movement);
		
	}
	
	@Override
	public void onCollide(){
		ShootEmUp.level1.particles.remove(this);
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
}

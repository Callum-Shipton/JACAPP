package Object;

import Display.Art;
import Display.DPDTRenderer;
import Math.Vector2;


public class Particle extends Entity implements Collidable{
	private int damage;
	private int range;
	private int distance;
	
	public Particle(float posX, float posY, int speed, int direction, int image, int damage, int range){
		super(posX, posY, speed, direction, image);
		this.damage = damage;
		this.range = range;
		distance = 0;
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
	
	public void render(DPDTRenderer r){
		r.draw(Art.particleID,new Vector2((posX + 16), (posY + 16)),new Vector2(32.0f, 32.0f), 0.0f, new Vector2(1.0f,(float)direction), new Vector2(1.0f,8.0f));
	}
	
	@Override
	public void onCollision(Collidable c) {
		// TODO Auto-generated method stub
		
		// Despawn and do damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}

package Object;

import Display.Art;
import Display.Renderer;
import Math.Vector2;


public class Particle extends Entity implements Collidable{
	private int damage;
	private int range;
	private int distance;
	
	public Particle(int posX, int posY, int speed, int direction, int image, int damage, int range){
		super(posX, posY, speed, direction, image);
		this.damage = damage;
		this.range = range;
		distance = 0;
	}
	
	public void update(){
		if(direction == 0){
			posY -= speed;
		}
		if(direction == 1){
			posY -= speed;
			posX += speed;
		}
		if(direction == 2){
			posX += speed;
		}
		if(direction == 3){
			posX += speed;
			posY += speed;
		}
		if(direction == 4){
			posY += speed;
		}
		if(direction == 5){
			posY += speed;
			posX -= speed;
		}
		if(direction == 6){
			posX -= speed;
		}
		if(direction == 7){
			posX -= speed;
			posY -= speed;
		}
	}
	
	public void render(Renderer r){
		r.draw(Art.particleID,new Vector2((float)(posX + 16), (float)(posY + 16)),new Vector2(32.0f, 32.0f), 0.0f, new Vector2(1.0f,(float)direction), new Vector2(1.0f,8.0f));
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

package Object;

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
		if(direction == 8){
			posY -= speed;
		}
		if(direction == 12){
			posY -= speed;
			posX -= speed;
		}
		if(direction == 4){
			posX -= speed;
		}
		if(direction == 6){
			posX -= speed;
			posY += speed;
		}
		if(direction == 2){
			posY += speed;
		}
		if(direction == 18){
			posY += speed;
			posX += speed;
		}
		if(direction == 16){
			posX += speed;
		}
		if(direction == 20){
			posX += speed;
			posY -= speed;
		}
	}
	
	public void render(Renderer r){
		r.draw(0,new Vector2((float)posX, (float)posY),new Vector2(100.0f, 100.0f), (float)direction*45, new Vector2(1.0f,1.0f), new Vector2(1.0f,1.0f));
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

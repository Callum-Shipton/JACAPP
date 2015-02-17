package Object;
import Display.DPDTRenderer;
import Main.ShootEmUp;
import Math.Vector2;

public abstract class Entity implements Collidable{

	protected float posX;
	protected float posY;
	protected float width;
	protected float height;
	protected int speed;
	protected int direction;
	protected int image;
	protected boolean collide;

	// Constructors

	public Entity(float spawn, float spawn2, int speed, int direction, int image){
		this.posX = spawn;
		this.posY = spawn2;
		this.speed = speed;
		this.direction = direction;
		this.image = image;
	}

	// Methods

	public void move(Vector2 moveVec) {
		collide = false;
		for (Entity collidable : ShootEmUp.level1.collidables) {
			if(collidable.doesCollide(posX + (moveVec.x() * speed), posY + (moveVec.y() * speed)) && (collidable != this)){
				collide = true;
			};
		}
		if(collide == false){
			posX += moveVec.x() * speed;
			posY += moveVec.y() * speed;
		} else {
			onCollide();
		}
	}

	public void render(DPDTRenderer r){
		r.draw(image,new Vector2(posX, posY),new Vector2(64.0f, 64.0f), 0.0f, new Vector2(0.0f,(float)direction),new Vector2(1.0f,8.0f));
	}
	
	//implemented methods
	
	public boolean doesCollide(float x, float y){
		if(collideFunction(x, y)){
			return true;
		}
		
		x += width;
		
		if(collideFunction(x, y)){
			return true;
		}
		
		y += height;
		
		if(collideFunction(x, y)){
			return true;
		}
		
		x -= width;
		
		if(collideFunction(x, y)){
			return true;
		}
		return false;
	}
	
	public boolean collideFunction(float x, float y){
		if(((x >= posX) && (x <= (posX + width))) && ((y >= posY) && (y <= (posY + height)))){
			return true;
		}
		return false;
	}
	
	public void onCollide(){
		return;
	}
	
	// Setters and getters

	public float getX() {
		return posX;
	}

	public void setX(float posX) {
		this.posX = posX;
	}

	public float getY() {
		return posY;
	}

	public void setY(float posY) {
		this.posY = posY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}

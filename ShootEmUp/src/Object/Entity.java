package Object;
import Display.DPDTRenderer;
import Math.Vector2;

public abstract class Entity implements Collidable{

	protected float posX;
	protected float posY;
	protected int speed;
	protected int direction;
	protected int image;

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
		posX += moveVec.x() * speed;
		posY += moveVec.y() * speed;
	}

	public void render(DPDTRenderer r){
		r.draw(image,new Vector2(posX, posY),new Vector2(64.0f, 64.0f), 0.0f, new Vector2(0.0f,(float)direction),new Vector2(1.0f,8.0f));
	}
	
	//implemented methods
	
	public boolean doesCollide(float x, float y){
		if(collideFunction(x, y)){
			return true;
		}
		
		x += 64.0f;
		
		if(collideFunction(x, y)){
			return true;
		}
		
		y += 64.0f;
		
		if(collideFunction(x, y)){
			return true;
		}
		
		x -= 64.0f;
		
		if(collideFunction(x, y)){
			return true;
		}
		return false;
	}
	
	public boolean collideFunction(float x, float y){
		if(((x >= posX) && (x <= (posX + 64.0f))) && ((y >= posY) && (y <= (posY + 64.0f)))){
			return true;
		}
		return false;
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

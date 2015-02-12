package Object;
import Display.Renderer;
import Math.Vector2;

public abstract class Entity {

	protected int posX;
	protected int posY;
	protected int speed;
	protected int direction;
	protected int image;

	// Constructors

	public Entity(int posX, int posY, int speed, int direction, int image){
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.direction = direction;
		this.image = image;
	}

	// Methods

	public void moveHorizontally(int direction) {
		posX += (speed * direction);
	}

	public void moveVertically(int direction) {
		posY += (speed * direction);
	}

	public void render(Renderer r){
		r.draw(image,new Vector2((float)posX, (float)posY),new Vector2(100.0f, 100.0f), (float)direction*45, new Vector2(1.0f,1.0f),new Vector2(1.0f,1.0f));
	}
	
	// Setters and getters

	public int getX() {
		return posX;
	}

	public void setX(int posX) {
		this.posX = posX;
	}

	public int getY() {
		return posY;
	}

	public void setY(int posY) {
		this.posY = posY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}

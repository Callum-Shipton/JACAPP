import java.io.File;
import java.io.IOException;

public abstract class Entity {

	private int posX;
	private int posY;
	private int speed;
	private int health;
	
	private Image image;

	// Constructors

	public Entity(int posX, int posY, int speed, int health, String image){
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.health = health;
		
		this.image = new Image(image);
	}

	// Methods

	public void moveHorizontally(int direction) {
		posX += (speed * direction);
	}

	public void moveVertically(int direction) {
		posY += (speed * direction);
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}

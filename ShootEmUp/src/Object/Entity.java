package Object;
import Display.Image;
import Display.Renderer;
import Math.Vector2;

public abstract class Entity {

	protected int posX;
	protected int posY;
	protected int speed;
	protected int direction;
	
	private Image image;

	// Constructors

	public Entity(int posX, int posY, int speed, int direction, String image){
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.direction = direction;
		
		this.image = new Image(image);
	}

	// Methods

	public void moveHorizontally(int direction) {
		posX += (speed * direction);
	}

	public void moveVertically(int direction) {
		posY += (speed * direction);
	}

	public void render(Renderer r){
		r.draw(0,new Vector2((float)posX, (float)posY),new Vector2(1.0f, 1.0f), 0.0f);
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
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image image){
		this.image = image;
	}
}

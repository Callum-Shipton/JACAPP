package Object;
import Display.DPDTRenderer;
import Main.ShootEmUp;
import Math.Vector2;

public abstract class Entity extends Collidable{
	protected int speed;
	protected int direction;
	protected int image;


	// Constructors

	public Entity(float x, float y, float width, float height, int speed, int direction, int image){
		super(x, y, width, height, false);
		this.speed = speed;
		this.direction = direction;
		this.image = image;
	}

	// Methods

	public void move(Vector2 moveVec) {
		boolean collide = false;
		NPC hit = null;
		for (NPC character : ShootEmUp.currentLevel.characters) {
			if(character.doesCollide(posX + (moveVec.x() * speed), posY, width, height) && (character != this)){
				collide = true;
				hit = character;
			};
		}
		for (Collidable wall : ShootEmUp.currentLevel.walls) {
			if(wall.doesCollide(posX + (moveVec.x() * speed), posY, width, height) && !wall.flat){
				collide = true;
			};
		}
		if(collide == false){
			posX += moveVec.x() * speed;
		} else {
			onCollide(hit);
		}
		collide = false;
		for (NPC character : ShootEmUp.currentLevel.characters) {
			if(character.doesCollide(posX, posY + (moveVec.y() * speed), width, height) && (character != this)){
				collide = true;
				hit = character;
			};
		}
		for (Collidable wall : ShootEmUp.currentLevel.walls) {
			if(wall.doesCollide(posX, posY + (moveVec.y() * speed), width, height) && !wall.flat){
				collide = true;
			};
		}
		if(collide == false){
			posY += moveVec.y() * speed;
		} else {
			onCollide(hit);
		}
	}

	public void render(DPDTRenderer r){
		r.draw(image,new Vector2(posX, posY),new Vector2(width, height), 0.0f, new Vector2(0.0f,(float)direction),new Vector2(1.0f,8.0f));
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

package Object;

import Display.DPDTRenderer;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;

public abstract class Entity extends Collidable {
	protected int speed;
	protected int direction;
	protected Image image;
	protected boolean flying;
	protected int team;

	// Constructors

	public Entity(float x, float y, float width, float height, int speed,
			int direction, Image image) {
		super(x, y, width, height, false);
		this.speed = speed;
		this.direction = direction;
		this.image = image;
	}

	// Methods

	public void move(Vector2 moveVec) {
		Vector4 vec = new Vector4(0.0f, 0.0f,0.0f,0.0f);
		boolean collide = false;
		NPC hit = null;
		for (NPC character : ShootEmUp.currentLevel.characters) {
			if ((character.doesCollide(posX + (moveVec.x() * speed), posY,
					width, height) != null) && (character != this)) {
				collide = true;
				vec = character.doesCollide(posX + (moveVec.x() * speed), posY,
						width, height);
				hit = character;
				break;
			}
		}
		for (Collidable wall : ShootEmUp.currentLevel.walls) {
			if (wall.doesCollide(posX + (moveVec.x() * speed), posY, width,
					height) != null) {
				if (!(wall.flat && flying)) {
					collide = true;
					vec = wall.doesCollide(posX + (moveVec.x() * speed), posY,
							width, height);
					break;
				}
			}
		}
		if (collide == false) {
			posX += moveVec.x() * speed;
		} else {
			if(vec.x() < speed){
				posX += (moveVec.x()*speed) - vec.x() - (moveVec.x()/Math.abs(moveVec.x()));
			}
			else if(vec.z() < speed){
				posX += (moveVec.x()*speed) - vec.z() - (moveVec.x()/Math.abs(moveVec.x()));
			}
			onCollide(hit);
		}
		collide = false;
		for (NPC character : ShootEmUp.currentLevel.characters) {
			if ((character.doesCollide(posX, posY + (moveVec.y() * speed),
					width, height) != null) && (character != this)) {
				collide = true;
				hit = character;
				vec = character.doesCollide(posX, posY + (moveVec.y() * speed), width,
						height);
				break;
			}
		}
		for (Collidable wall : ShootEmUp.currentLevel.walls) {
			if (wall.doesCollide(posX, posY + (moveVec.y() * speed), width,
					height) != null) {
				if (!(wall.flat && flying)) {
					collide = true;
					vec = wall.doesCollide(posX, posY + (moveVec.y() * speed), width,
							height);
					break;
				}
			}
		}
		if (collide == false) {
			posY += moveVec.y() * speed;
		} else {
			onCollide(hit);
			if(vec.y() < speed){
				posY += (moveVec.y()* speed) - vec.y() - (moveVec.y()/Math.abs(moveVec.y()));
			}
			else if(vec.w() < speed){
				posY += (moveVec.y()* speed) - vec.w() - (moveVec.y()/Math.abs(moveVec.y()));
			}
		}
	}

	public void render(DPDTRenderer r) {
		r.draw(image.getID(), new Vector2(posX, posY), new Vector2(width, height),
				0.0f, new Vector2(0.0f, (float) direction), new Vector2(image.getFWidth(),
						image.getFHeight()));
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

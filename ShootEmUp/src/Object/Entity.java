package Object;

import java.util.HashSet;

import Display.DPDTRenderer;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;

public abstract class Entity extends Collidable {
	protected int speed;
	protected int direction;
	protected Image image;
	protected boolean canfly;
	protected boolean animating;
	protected int animID;
	protected int animTime;
	protected int team;
	protected HashSet<Vector2> gridPos;
	public boolean destroy = false;

	// Constructors

	public Entity(float x, float y) {
		super(x, y, false);
		this.animID = 0;
		this.animating = false;
		this.animTime = 6;
		gridPos = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.addEntity(gridPos, this);
	}

	public void update() {
		if (animating) {
			animID++;
			if (animID >= image.getFWidth() * animTime)
				animID = 0;
		}
	}

	// Methods

	public void move(Vector2 moveVec) {
		Vector4 vec = new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap
				.getEntites(gridPos);
		boolean collide = false;
		Character hit = null;
		int maxX = 1 + (int) Math.ceil((posX + (moveVec.x() * speed)) / 32);
		int minX = -1 + (int) Math.floor((posX - (moveVec.x() * speed)) / 32);
		int maxY = 1 + (int) Math.ceil((posY + (moveVec.y() * speed)) / 32);
		int minY = -1 + (int) Math.floor((posY - (moveVec.y() * speed)) / 32);
		Collidable wall;
		if(Math.abs(moveVec.x()) > 0){
		for (Entity character : entities) {
			if (character instanceof Character) {
				if ((character.doesCollide(posX + (moveVec.x() * speed), posY,
						width, height) != null) && (character != this)) {
					collide = true;
					vec = character.doesCollide(posX + (moveVec.x() * speed),
							posY, width, height);
					hit = (Character) character;
					break;
				}
			}
		}

		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				if (i >= 0 && j >= 0
						&& i <= ShootEmUp.currentLevel.getWidth() - 1
						&& j <= ShootEmUp.currentLevel.getHeight() - 1) {
					wall = ShootEmUp.currentLevel.walls
							.get(new Vector2(i,j));
					if (wall != null) {
						if (wall.doesCollide(posX + (moveVec.x() * speed),
								posY, width, height) != null) {
							if (!(wall.flat && canfly)) {
								collide = true;
								vec = wall.doesCollide(posX
										+ (moveVec.x() * speed), posY, width,
										height);
								break;
							}
						}
					}
				}
			}
		}

		if (collide == false) {
			posX += moveVec.x() * speed;
		} else {
			if (Math.abs(vec.x()) < speed) {
				posX += (moveVec.x() * speed) - vec.x()
						- (moveVec.x() / Math.abs(moveVec.x()));
			} else if(Math.abs(vec.x()) >= speed); 
			else if (Math.abs(vec.z()) < speed) {
				posX += (moveVec.x() * speed) - vec.z()
						- (moveVec.x() / Math.abs(moveVec.x()));
			}
			onCollide(hit);
		}
		}
		collide = false;
		if(Math.abs(moveVec.y()) > 0){
		for (Entity character : entities) {
			if (character instanceof Character) {
				if ((character.doesCollide(posX, posY + (moveVec.y() * speed),
						width, height) != null) && (character != this)) {
					collide = true;
					hit = (Character) character;
					vec = character.doesCollide(posX, posY
							+ (moveVec.y() * speed), width, height);
					break;
				}
			}
		}

		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				if (i >= 0 && j >= 0
						&& i <= ShootEmUp.currentLevel.getWidth() - 1
						&& j <= ShootEmUp.currentLevel.getHeight() - 1) {
					wall = ShootEmUp.currentLevel.walls
							.get(new Vector2(i,j));
					if (wall != null) {
						if (wall.doesCollide(posX,
								posY + (moveVec.y() * speed), width, height) != null) {
							if (!(wall.flat && canfly)) {
								collide = true;
								vec = wall.doesCollide(posX,
										posY + (moveVec.y() * speed), width,
										height);
								break;
							}
						}
					}
				}
			}
		}

		if (collide == false) {
			posY += moveVec.y() * speed;
		} else {
			onCollide(hit);
			if (Math.abs(vec.y()) < speed) {
				posY += (moveVec.y() * speed) - vec.y()
						- (moveVec.y() / Math.abs(moveVec.y()));
			} else if(Math.abs(vec.y()) >= speed); 
			else if (Math.abs(vec.w()) < speed) {
				posY += (moveVec.y() * speed) - vec.w()
						- (moveVec.y() / Math.abs(moveVec.y()));
			}
		}
		}
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, this);
		gridPos = newGrid;
	}

	public void render(DPDTRenderer r) {
		r.draw(image, new Vector2(posX, posY), new Vector2(width, height),
				0.0f, new Vector2((float) Math.floor(animID / animTime),
						(float) direction), new Vector2(image.getFWidth(),
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

package Object;

import java.util.HashSet;

import Display.DPDTRenderer;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;

public abstract class Entity extends Collidable {
	protected int speed;
	protected boolean canfly;
	private float posX;
	private float posY;
	private float width;
	private float height;
	private int team;
	protected HashSet<Vector2> gridPos;
	public boolean destroy = false;

	// Constructors

	public Entity(float x, float y) {
		super(x, y, false);
		gridPos = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.addEntity(gridPos, this);
	}

	public void update() {

	}

	// Methods

	public void move(Vector2 moveVec) {
		Vector4 vec = new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap
				.getEntites(gridPos);
		boolean collide = false;
		Character hit = null;
		int maxX = 1 + (int) Math.ceil((getPosX() + (Math.round(moveVec.x() * speed))) / 32);
		int minX = -1 + (int) Math.floor((getPosX() - (Math.round(moveVec.x() * speed))) / 32);
		int maxY = 1 + (int) Math.ceil((getPosY() + (Math.round(moveVec.y() * speed))) / 32);
		int minY = -1 + (int) Math.floor((getPosY() - (Math.round(moveVec.y() * speed))) / 32);
		Collidable wall;
		if(Math.abs(moveVec.x()) > 0){
		for (Entity character : entities) {
			if (character instanceof Character) {
				if ((character.doesCollide(getPosX() + (Math.round(moveVec.x() * speed)), getPosY(),
						getWidth(), getHeight()) != null) && (character != this)) {
					collide = true;
					vec = character.doesCollide(getPosX() + (Math.round(moveVec.x() * speed)),
							getPosY(), getWidth(), getHeight());
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
						if (wall.doesCollide(getPosX() + (Math.round(moveVec.x() * speed)),
								getPosY(), getWidth(), getHeight()) != null) {
							if (!(wall.flat && canfly)) {
								collide = true;
								vec = wall.doesCollide(getPosX()
										+ (Math.round(moveVec.x() * speed)), getPosY(), getWidth(),
										getHeight());
								break;
							}
						}
					}
				}
			}
		}

		if (collide == false) {
			setPosX(getPosX() + Math.round(moveVec.x() * speed));
		} else {
			if (Math.abs(vec.x()) < speed) {
				setPosX(getPosX() + ((Math.round(moveVec.x() * speed)) - vec.x()
						- (moveVec.x() / Math.abs(moveVec.x()))));
			} else if(Math.abs(vec.x()) >= speed); 
			else if (Math.abs(vec.z()) < speed) {
				setPosX(getPosX() + ((Math.round(moveVec.x() * speed)) - vec.z()
						- (moveVec.x() / Math.abs(moveVec.x()))));
			}
			onCollide(hit);
		}
		}
		collide = false;
		if(Math.abs(moveVec.y()) > 0){
		for (Entity character : entities) {
			if (character instanceof Character) {
				if ((character.doesCollide(getPosX(), getPosY() + (Math.round(moveVec.y() * speed)),
						getWidth(), getHeight()) != null) && (character != this)) {
					collide = true;
					hit = (Character) character;
					vec = character.doesCollide(getPosX(), getPosY()
							+ (Math.round(moveVec.y() * speed)), getWidth(), getHeight());
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
						if (wall.doesCollide(getPosX(),
								getPosY() + (Math.round(moveVec.y() * speed)), getWidth(), getHeight()) != null) {
							if (!(wall.flat && canfly)) {
								collide = true;
								vec = wall.doesCollide(getPosX(),
										getPosY() + (Math.round(moveVec.y() * speed)), getWidth(),
										getHeight());
								break;
							}
						}
					}
				}
			}
		}

		if (collide == false) {
			setPosY(getPosY() + Math.round(moveVec.y() * speed));
		} else {
			onCollide(hit);
			if (Math.abs(vec.y()) < speed) {
				setPosY(getPosY() + ((Math.round(moveVec.y() * speed)) - vec.y()
						- (moveVec.y() / Math.abs(moveVec.y()))));
			} else if(Math.abs(vec.y()) >= speed); 
			else if (Math.abs(vec.w()) < speed) {
				setPosY(getPosY() + ((Math.round(moveVec.y() * speed)) - vec.w()
						- (moveVec.y() / Math.abs(moveVec.y()))));
			}
		}
		}
		if(!destroy){
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, this);
		gridPos = newGrid;
		}
	}

	public void render(DPDTRenderer r) {
		r.draw(image, new Vector2(getPosX(), getPosY()), new Vector2(getWidth(), getHeight()),
				0.0f, new Vector2((float) Math.floor(animID / animTime),
						(float) getDirection()), new Vector2(image.getFWidth(),
						image.getFHeight()));
	}

	// Setters and getters


	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}

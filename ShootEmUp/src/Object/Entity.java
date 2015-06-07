package Object;

import java.util.HashSet;

import Display.DPDTRenderer;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;

public abstract class Entity{
	private float posX;
	private float posY;
	private float width;
	private float height;
	private int team;
	public boolean destroy = false;

	// Constructors

	public Entity(float x, float y) {
		gridPos = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.addEntity(gridPos, this);
	}

	public void update() {

	}

	// Methods

	public void render(DPDTRenderer r) {
		r.draw(image, new Vector2(getPosX(), getPosY()), new Vector2(getWidth(), getHeight()),
				0.0f, new Vector2((float) Math.floor(animID / animTime),
						(float) getDirection()), new Vector2(image.getFWidth(),
						image.getFHeight()));
	}

	// Setters and getters

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

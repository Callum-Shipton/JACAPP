package Object;

import Display.Art;
import Main.ShootEmUp;
import Math.Vector2;

public class Particle extends Entity {
	private float distance = 0.0f;
	private Weapon weapon;
	

	public Particle(float posX, float posY, int direction,  Weapon weapon, int team) {
		super(posX, posY);
		canfly = true;
		this.weapon = weapon;
		speed = 10;
		this.setDirection(direction);
		image = Art.earthMagic;
		setWidth(32f);
		setHeight(32f);
		this.setTeam(team);
	}

	public void update() {
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (getDirection() >= 1 && getDirection() <= 3) {
			movement.add(1.0f, 0.0f);
		}
		if (getDirection() >= 5) {
			movement.add(-1.0f, 0.0f);
		}
		if (getDirection() <= 1 || getDirection() >= 7) {
			movement.add(0.0f, -1.0f);
		}
		if (getDirection() >= 3 && getDirection() <= 5) {
			movement.add(0.0f, 1.0f);
		}
		movement.normalize();
		move(movement);
	}

	@Override
	public void onCollide(Character hit) {
		setDestroy(true);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
		if (hit != null && getTeam() != hit.getTeam()) {
			hit.damage(weapon.getDamage());
		}
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
}

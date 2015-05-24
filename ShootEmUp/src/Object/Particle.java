package Object;

import Display.Art;
import Display.Image;
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
		this.direction = direction;
		image = Art.particle;
		width = 32f;
		height = 32f;
		this.team = team;
	}

	public void update() {
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (direction >= 1 && direction <= 3) {
			movement.add(1.0f, 0.0f);
		}
		if (direction >= 5) {
			movement.add(-1.0f, 0.0f);
		}
		if (direction <= 1 || direction >= 7) {
			movement.add(0.0f, -1.0f);
		}
		if (direction >= 3 && direction <= 5) {
			movement.add(0.0f, 1.0f);
		}
		movement.normalize();
		move(movement);
	}

	@Override
	public void onCollide(Character hit) {
		ShootEmUp.currentLevel.particles.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
		if (hit != null && team != hit.team) {
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

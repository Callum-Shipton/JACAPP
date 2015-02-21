package Object;

import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;

public abstract class NPC extends Entity {
	protected int health;
	protected Weapon weapon;

	public NPC(float x, float y, float width, float height, int speed, int direction, Image image) {
		super(x, y, width, height, speed, direction, image);
		flying = false;
		health = 100;
		weapon = new Weapon(10, 10);
	}

	public void update() {
		checkDead();
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (ShootEmUp.currentLevel.getPlayer().getY() < posY - speed) {
			movement.add(0.0f, -1.0f);
		}
		if (ShootEmUp.currentLevel.getPlayer().getX() < posX - speed) {
			movement.add(-1.0f, 0.0f);
		}
		if (ShootEmUp.currentLevel.getPlayer().getY() > posY + speed) {
			movement.add(0.0f, 1.0f);
		}
		if (ShootEmUp.currentLevel.getPlayer().getX() > posX + speed) {
			movement.add(1.0f, 0.0f);
		}
		if (movement.length() > 0) {
			if (movement.length() > 1)
				movement.normalize();
			move(movement);
			direction = (int) (Math.round(movement.Angle()) / 45);
		}
		weapon.shoot(posX, posY, direction, team);
	}

	public void checkDead() {
		if (health <= 0) {
			ShootEmUp.currentLevel.characters.remove(this);
		}
	}

	public int getHealth() {
		return health;
	}

	public void damage(int damage) {
		this.health -= damage;
	}
}

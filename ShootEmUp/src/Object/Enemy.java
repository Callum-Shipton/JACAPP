package Object;

import Display.Art;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;

public class Enemy extends Character {

	private int counter = 0;
	
	public Enemy(float x, float y, float w, float h, int speed, int direction,
			Image image) {
		super(x, y, w, h, speed, direction, image);
		width = 64;
		height = 64;
		health = 5;
		team = 1;
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
		counter++;
		if (counter == 30){
			weapon.shoot(posX, posY, direction, team);
			counter = 0;
		}
	}
	
	public void checkDead() {
		if (health <= 0) {
			ShootEmUp.currentLevel.characters.remove(this);
			Exp drop = new Exp(posX, posY, width, height, flat, Art.exp);
		}
	}
}

package Object;

import Display.Art;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;

public class Enemy extends Character {

	private int counter = 0;
	
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	public void update() {
		super.update();
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
			animating = true;
			move(movement);
			direction = (int) (Math.round(movement.Angle()) / 45);
		}
		else animating = false;
		counter++;
		if (counter == 30){
			weapon.shoot(posX, posY, direction, team);
			counter = 0;
		}
	}
	
	public void checkDead() {
		if (health <= 0) {
			ShootEmUp.currentLevel.characters.remove(this);
			ShootEmUp.currentLevel.experience.add(new Exp(posX, posY));
			ShootEmUp.currentLevel.coins.add(new Coin(posX + 32, posY + 32));
		}
	}
}

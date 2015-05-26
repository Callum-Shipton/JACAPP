package Object.Enemies;

import Display.Art;
import Object.Enemy;
import Object.Weapon;

public class RedSquare extends Enemy {

	public RedSquare(float x, float y) {
		super(x, y);
		width = 64;
		height = 64;
		health = 5;
		team = 1;
		speed = 5;
		direction = 0;
		image = Art.enemy;
		canfly = false;
		setMaxHealth(5);
		health = maxHealth;
		weapon = new Weapon(1,10);
	}

}

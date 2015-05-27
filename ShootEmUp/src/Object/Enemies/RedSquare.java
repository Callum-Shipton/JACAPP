package Object.Enemies;

import Display.Art;
import Object.Enemy;
import Object.Weapon;

public class RedSquare extends Enemy {

	public RedSquare(float x, float y) {
		super(x, y);
		width = 63.99f;
		height = 63.99f;
		health = 5;
		team = 1;
		speed = 5;
		direction = 0;
		image = Art.enemy;
		canfly = false;
		setMaxHealth(5);
		health = maxHealth;
		weapon = new Weapon(0,0,null,1,10);
	}

}

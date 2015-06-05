package Object.Enemies;

import Display.Art;
import Object.Enemy;
import Object.Weapon;

public class RedSquare extends Enemy {

	public RedSquare(float x, float y) {
		super(x, y);
		setWidth(64.0f);
		setHeight(64.0f);
		health = 5;
		setTeam(1);
		speed = 5;
		setDirection(0);
		image = Art.enemy;
		canfly = false;
		setMaxHealth(5);
		health = maxHealth;
		weapon = new Weapon(0,0,null,1,10);
	}

}

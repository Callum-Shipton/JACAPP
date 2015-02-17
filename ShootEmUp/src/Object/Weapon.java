package Object;

import Display.Art;
import Main.ShootEmUp;


public class Weapon {
	
	private int damage;
	private int range;
	
	public Weapon(int damage, int range){
		this.damage = damage;
		this.range = range;
	}
	
	public void shoot(float posX, float posY, int direction){
		ShootEmUp.level1.particles.add(new Particle(posX + 16, posY + 16, 10, direction, Art.particleID));
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
}

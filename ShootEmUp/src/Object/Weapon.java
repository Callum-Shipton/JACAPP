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
		if(direction >= 1 && direction <= 3){
			posX += 44;
		}
		if(direction >= 5){
			posX -= 44;
		}
		if(direction <= 1 || direction >= 7){
			posY -= 44;
		}
		if(direction >= 3 && direction <= 5){
			posY += 44;
		}
		ShootEmUp.level1.particles.add(new Particle(posX + 16, posY + 16, 32.0f, 32.0f, 10, direction, Art.particleID));
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

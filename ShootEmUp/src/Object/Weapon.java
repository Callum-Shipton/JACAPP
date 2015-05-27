package Object;

import Display.Image;
import Main.ShootEmUp;

public class Weapon extends Entity{

	private int damage;
	private int range;

	public Weapon(float posX, float posY, Image image, int damage, int range) {
		super(posX , posY);
		width = 16;
		height = 16;
		this.image = image;
		animating = true;
		this.damage = damage;
		this.range = range;
	}

	public void remove(){
		ShootEmUp.currentLevel.weapons.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
	
	public void shoot(float posX, float posY, int direction, int team) {
		if (direction >= 1 && direction <= 3) {
			posX += 44;
		}
		if (direction >= 5) {
			posX -= 44;
		}
		if (direction <= 1 || direction >= 7) {
			posY -= 49;
		}
		if (direction >= 3 && direction <= 5) {
			posY += 49;
		}
		ShootEmUp.currentLevel.particles.add(new Particle(posX + 16, posY + 16, direction, this, team));
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

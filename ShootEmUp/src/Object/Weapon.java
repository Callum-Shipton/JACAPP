package Object;

import Display.Image;
import Main.ShootEmUp;

public class Weapon{

	private int damage;
	private int range;
	private int firerate;
	private boolean melee;
	private int manaCost;

	public Weapon(int damage, int range, int firerate, boolean melee, int manaCost) {
		this.damage = damage;
		this.range = range;
		this.firerate = firerate;
		this.melee = melee;
		this.manaCost = manaCost;
	}
	
	public void attack(Entity e, int direction) {
		float posX = e.getPosX();
		float posY = e.getPosY();
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
		ShootEmUp.currentLevel.particles.add(new Particle(posX + 16, posY + 16, direction, this, e.getTeam()));
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

	public boolean isMelee() {
		return melee;
	}

	public void setMelee(boolean melee) {
		this.melee = melee;
	}

	public int getFirerate() {
		return firerate;
	}

	public void setFirerate(int firerate) {
		this.firerate = firerate;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
}

package Object;

import Components.ComponentType;
import Components.Attack.MageAttack;
import Components.Collision.MoveCollision;
import Components.Control.AIControl;
import Components.Control.LineControl;
import Components.Control.PlayerControl;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Graphical.PlayerGraphics;
import Components.Movement.BaseMovement;
import Components.Movement.BasicMovement;
import Components.Spawn.PointSpawn;
import Display.Art;
import Main.ShootEmUp;
import Math.Vector2;

public class Weapon{

	private int damage;
	private int range;
	private boolean melee;
	private int manaCost;
	private int team;
	private int fireRate;

	public Weapon(int damage, int range, int fireRate, boolean melee, int manaCost) {
		this.damage = damage;
		this.range = range;
		this.setFireRate(fireRate);
		this.melee = melee;
		this.manaCost = manaCost;
	}
	
	public void attack(Entity e, int direction) {
		BaseGraphics BG = (BaseGraphics) e.getComponent(ComponentType.GRAPHICS);
		float posX = BG.getX();
		float posY = BG.getY();
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
		//create particle
		Entity particle = new Entity();
		AnimatedGraphics g = new AnimatedGraphics(Art.fireMagic, Art.base);
		g.setDirection(direction);
		particle.addComponent(g);
		PointSpawn s = new PointSpawn(g, new Vector2(posX,posY),particle);
		MoveCollision c = new MoveCollision();
		BasicMovement m = new BasicMovement(particle, c, g, 10);
		particle.addComponent(s);
		particle.addComponent(c);
		particle.addComponent(m);
		particle.addComponent(new LineControl(g, m));
		
		ShootEmUp.currentLevel.newEntities.add(particle);
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

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
	
	public int getTeam(){
		return team;
	}
	
	public void setTeam(int team){
		this.team = team;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
}

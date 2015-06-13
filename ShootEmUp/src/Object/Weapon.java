package Object;

import Components.ComponentType;
import Components.Collision.HitCollision;
import Components.Control.LineControl;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Movement.FlyingMovement;
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
		//create particle
		Entity particle = new Entity();
		AnimatedGraphics g = new AnimatedGraphics(Art.fireMagic, Art.base, false);
		g.setDirection(direction);
		particle.addComponent(g);
		switch(direction){
		case 0:	posX += (BG.getWidth() - g.getWidth())/2;
				posY -= g.getHeight();
				break;
		case 1: posX += BG.getWidth();
				posY -= g.getHeight();
				break;
		case 2:	posX += BG.getWidth();
				posY += (BG.getHeight() - g.getHeight())/2;
				break;
		case 3: posX += BG.getWidth();
				posY += BG.getHeight();
				break;
		case 4: posX += (BG.getWidth() - g.getWidth())/2;
				posY += BG.getHeight();
				break;
		case 5: posX -= g.getWidth(); 
				posY += BG.getHeight();
				break;
		case 6: posX -= g.getWidth();
				posY += (BG.getHeight() - g.getHeight())/2;
				break;
		case 7: posX -= g.getWidth();
				posY -= g.getHeight();
		}
		PointSpawn s = new PointSpawn(g, new Vector2(posX,posY),particle);
		HitCollision c = new HitCollision(particle, this);
		FlyingMovement m = new FlyingMovement(particle, c, g, 10);
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

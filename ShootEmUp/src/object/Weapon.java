package object;

import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.collision.HitCollision;
import components.control.LineControl;
import components.control.RangeControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.inventory.TypeWeapon;
import components.movement.FlyingMovement;
import components.spawn.PointSpawn;
import display.Art;
import display.Image;

public class Weapon extends InventoryItem {

	
	private TypeWeapon type;
	private int damage;
	private int range;
	private int fireRate;
	private boolean melee;
	private int manaCost;
	private int team;
    private Image particleImage;
    private Element element;
    
	public Weapon(TypeWeapon type, int damage, int range, int fireRate, boolean melee, int manaCost, Element element, int team, Image particleImage, Image inventoryImage) {
		this.type = type;
		this.damage = damage;
		this.range = range;
		this.fireRate = fireRate;
		this.melee = melee;
		this.manaCost = manaCost;
		this.element = element;
		this.team = team;
		this.particleImage = particleImage;
		this.inventoryImage = inventoryImage;
		typePickup = TypePickup.WEAPON;
	}
	
	public void attack(Entity e, int direction) {
			BaseGraphics BG = (BaseGraphics) e.getComponent(TypeComponent.GRAPHICS);
			float posX = BG.getX();
			float posY = BG.getY();
			//create particle
			Entity particle = new Entity();
			AnimatedGraphics g = new AnimatedGraphics(particleImage, Art.base, false);
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
			particle.addComponent(new RangeControl(g, m, this.range));
			
			ShootEmUp.currentLevel.spawner.checkSpawn(particle);
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
	
	public TypeWeapon getType(){
		return type;
	}
	
	public Image getParticleImage(){
		return particleImage;
	}
	
	public Element getElement() {
		return element;
	}

}

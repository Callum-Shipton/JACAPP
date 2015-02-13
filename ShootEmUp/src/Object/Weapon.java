package Object;

import Display.Art;
import Main.Level;


public class Weapon {
	
	private int damage;
	private int range;
	
	public Weapon(int damage, int range){
		this.damage = damage;
		this.range = range;
	}
	
	public void shoot(float posX, float posY, int direction){
		Level.p = new Particle(posX, posY, 10, direction, Art.particleID, 1, 50);
	}
}

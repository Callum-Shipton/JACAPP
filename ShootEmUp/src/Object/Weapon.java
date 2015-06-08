package Object;

import Components.Attack.MageAttack;
import Components.Collision.MoveCollision;
import Components.Control.PlayerControl;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.PlayerGraphics;
import Components.Movement.BasicMovement;
import Components.Spawn.PointSpawn;
import Main.ShootEmUp;
import Math.Vector2;

public class Weapon{

	private int damage;
	private int range;
	private boolean melee;
	private int manaCost;
	private int team;

	public Weapon(int damage, int range, boolean melee, int manaCost) {
		this.damage = damage;
		this.range = range;
		this.melee = melee;
		this.manaCost = manaCost;
	}
	
	public void attack(Entity e, int direction) {
		float posX = e.getX();
		float posY = e.getY();
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
		AnimatedGraphics g = new AnimatedGraphics();
		PointSpawn s = new PointSpawn(g, new Vector2(80,80),particle);
		MageAttack a = new MageAttack(s);
		MoveCollision c = new MoveCollision();
		BasicMovement m = new BasicMovement(player, c, g);
		player.addComponent(g);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(c);
		player.addComponent(m);
		player.addComponent(new PlayerControl(g, a, m));
		
		ShootEmUp.currentLevel.characters.add(particle);
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
	
	public int getTeam(){
		return team;
	}
	
	public void setTeam(int team){
		this.team = team;
	}
}

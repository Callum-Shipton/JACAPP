package Components.Attack;

import java.util.Random;

import Components.Message;
import Components.Graphical.AnimatedGraphics;
import Components.Spawn.BaseSpawn;
import Components.Spawn.PointSpawn;
import Display.Art;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;
import Object.Weapon;

public class MeleeAttack extends BaseAttack implements AttackComponent {

	AnimatedGraphics AG;
	BaseSpawn BS;
	
	public MeleeAttack(BaseSpawn BS, AnimatedGraphics AG, Weapon weapon, int health, int healthRegen, int maxHealth){
		this.AG = AG;
		this.BS = BS;
		this.weapon = weapon;
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealth = maxHealth;
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(weapon.isMelee()){
				weapon.attack(e, dir);
			}
			fireRate = weapon.getFireRate();
		}
	}

	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			drop(e);
			e.send(Message.ENTITY_DIED);
		}
		
		if (healthRegen <= 0) {
			healthRegen = 100;
			if (getHealth() < getMaxHealth()) {
				setHealth(getHealth() + 1);
			}
		}
		healthRegen--;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	public void drop(Entity e) {
		//give player exp
	
		//create coins
		Entity coin = new Entity();
		AnimatedGraphics coinG = new AnimatedGraphics(Art.coin, Art.base);
		PointSpawn coinS = new PointSpawn(coinG, new Vector2(AG.getX() + AG.getWidth() - coinG.getWidth(), AG.getY() + AG.getHeight() - coinG.getHeight()), coin);
		coin.addComponent(coinG);
		coin.addComponent(coinS);
		ShootEmUp.currentLevel.newEntities.add(coin);
		
		
		//create armour, item or weapon
		Random rand = new Random();
		int prob = rand.nextInt(3);
		if(prob == 0 ) {
			int armourProb = rand.nextInt(5);
			Entity armour = new Entity();
			AnimatedGraphics armourG;
			if(armourProb == 0){
				armourG = new AnimatedGraphics(Art.shoes, Art.base);
			} else if(armourProb == 1){
				armourG = new AnimatedGraphics(Art.legs, Art.base);
			} else if(armourProb == 2){
				armourG = new AnimatedGraphics(Art.chest, Art.base);
			} else {
				armourG = new AnimatedGraphics(Art.helmet, Art.base);
			}
			PointSpawn armourS = new PointSpawn(armourG, new Vector2(AG.getX(), AG.getY()), armour);
			armour.addComponent(armourG);
			armour.addComponent(armourS);
			ShootEmUp.currentLevel.newEntities.add(armour);
		} else if(prob == 1) {
			int itemProb = rand.nextInt(4);
			Entity item = new Entity();
			AnimatedGraphics itemG;
			if(itemProb == 0) {
				itemG = new AnimatedGraphics(Art.healthPotion, Art.base);
			} else if(itemProb == 1){
				itemG = new AnimatedGraphics(Art.manaPotion, Art.base);
			} else if(itemProb == 2){
				itemG = new AnimatedGraphics(Art.speedPotion, Art.base);
			} else {
				itemG = new AnimatedGraphics(Art.knockbackPotion, Art.base);
			}
			PointSpawn itemS = new PointSpawn(itemG, new Vector2(AG.getX() + AG.getWidth() - itemG.getWidth(),AG.getY()), item);
			item.addComponent(itemG);
			item.addComponent(itemS);
			ShootEmUp.currentLevel.newEntities.add(item);
		} else {	
			Entity weapon = new Entity();
			AnimatedGraphics weaponG = new AnimatedGraphics(Art.bow, Art.base);
			PointSpawn weaponS = new PointSpawn(weaponG, new Vector2(AG.getX(), AG.getY() + AG.getHeight() - weaponG.getHeight()), weapon);
			weapon.addComponent(weaponG);
			weapon.addComponent(weaponS);
			ShootEmUp.currentLevel.newEntities.add(weapon);
		}
	}
}

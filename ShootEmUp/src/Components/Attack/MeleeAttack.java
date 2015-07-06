package Components.Attack;

import java.util.Random;

import Components.ComponentType;
import Components.Message;
import Components.Collision.PickupCollision;
import Components.Graphical.AnimatedGraphics;
import Components.Spawn.BaseSpawn;
import Components.Spawn.PointSpawn;
import Components.Inventory.PlayerInventory;
import Display.Art;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;
import Object.Weapon;

public class MeleeAttack extends BaseAttack implements AttackComponent {

	AnimatedGraphics AG;
	
	public MeleeAttack(BaseSpawn BS, AnimatedGraphics AG, Weapon weapon, int health, int healthRegen, int maxHealth){
		this.AG = AG;
		this.BS = BS;
		this.setWeapon(weapon);
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealthRegen = healthRegen;
		this.maxHealth = maxHealth;
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(getWeapon().isMelee()){
				getWeapon().attack(e, dir);
			}
			fireRate = getWeapon().getFireRate();
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
			healthRegen = maxHealthRegen;
			if (getHealth() < getMaxHealth()) {
				setHealth(getHealth() + 1);
			}
		}
		healthRegen--;
	}
	
	@Override
	public void damage(int damage, Entity e) {
		this.health -= damage;
		if(health <= 0) {
			drop(e);
			e.send(Message.ENTITY_DIED);
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	public void drop(Entity e) {
		//give player exp
		((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY)).giveExp(1);
	
		//create coins
		Entity coin = new Entity();
		AnimatedGraphics coinG = new AnimatedGraphics(Art.coin, Art.base, true);
		PointSpawn coinS = new PointSpawn(coinG, new Vector2(AG.getX() + AG.getWidth() - coinG.getWidth(), AG.getY() + AG.getHeight() - coinG.getHeight()), coin);
		coin.addComponent(coinG);
		PickupCollision coinC = new PickupCollision(coin);
		coin.addComponent(coinS);
		coin.addComponent(coinC);
		ShootEmUp.currentLevel.newEntities.add(coin);
		
		
		//create armour, item or weapon
		Random rand = new Random();
		int prob = rand.nextInt(100);
		if(prob <= 25 ) {
			int itemProb = rand.nextInt(4);
			Entity item = new Entity();
			AnimatedGraphics itemG;
			if(itemProb == 0) {
				itemG = new AnimatedGraphics(Art.healthPotion, Art.base, true);
			} else if(itemProb == 1){
				itemG = new AnimatedGraphics(Art.manaPotion, Art.base, true);
			} else if(itemProb == 2){
				itemG = new AnimatedGraphics(Art.speedPotion, Art.base, true);
			} else {
				itemG = new AnimatedGraphics(Art.knockbackPotion, Art.base, true);
			}
			PointSpawn itemS = new PointSpawn(itemG, new Vector2(AG.getX() + AG.getWidth() - itemG.getWidth(),AG.getY()), item);
			item.addComponent(itemG);
			PickupCollision itemC = new PickupCollision(item);
			item.addComponent(itemS);
			item.addComponent(itemC);
			ShootEmUp.currentLevel.newEntities.add(item);
		} else if(prob <= 40) {
			int armourProb = rand.nextInt(5);
			Entity armour = new Entity();
			AnimatedGraphics armourG;
			if(armourProb == 0){
				armourG = new AnimatedGraphics(Art.shoes, Art.base, true);
			} else if(armourProb == 1){
				armourG = new AnimatedGraphics(Art.legs, Art.base, true);
			} else if(armourProb == 2){
				armourG = new AnimatedGraphics(Art.chest, Art.base, true);
			} else {
				armourG = new AnimatedGraphics(Art.helmet, Art.base, true);
			}
			PointSpawn armourS = new PointSpawn(armourG, new Vector2(AG.getX(), AG.getY()), armour);
			armour.addComponent(armourG);
			PickupCollision armourC = new PickupCollision(armour);
			armour.addComponent(armourS);
			armour.addComponent(armourC);
			ShootEmUp.currentLevel.newEntities.add(armour);
		} else if(prob <= 50) {	
			Entity weapon = new Entity();
			AnimatedGraphics weaponG = new AnimatedGraphics(Art.bow, Art.base, true);
			PointSpawn weaponS = new PointSpawn(weaponG, new Vector2(AG.getX(), AG.getY() + AG.getHeight() - weaponG.getHeight()), weapon);
			weapon.addComponent(weaponG);
			PickupCollision weaponC = new PickupCollision(weapon);
			weapon.addComponent(weaponS);
			weapon.addComponent(weaponC);
			ShootEmUp.currentLevel.newEntities.add(weapon);
		}
	}
}

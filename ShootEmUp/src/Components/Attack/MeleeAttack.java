package Components.Attack;

import java.util.Random;

import Components.ComponentType;
import Components.Message;
import Components.Collision.PickupCollision;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Spawn.BaseSpawn;
import Components.Spawn.PointSpawn;
import Components.Inventory.ArmourType;
import Components.Inventory.CoinType;
import Components.Inventory.PickupType;
import Components.Inventory.PlayerInventory;
import Components.Inventory.PotionType;
import Components.Inventory.WeaponType;
import Display.Art;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;
import Object.Weapon;

public class MeleeAttack extends BaseAttack implements AttackComponent {

	BaseGraphics BG;
	
	public MeleeAttack(BaseSpawn BS, BaseGraphics BG, Weapon weapon, int health, int healthRegen, int maxHealth){
		this.BG = BG;
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
			//if(getWeapon().isMelee()){
				getWeapon().attack(e, dir);
		//	}
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
		
		healthRegen();
	}
	
	@Override
	public void damage(int damage, Entity e) {
		this.health -= damage;
		if(health <= 0) {
			drop(e);
			e.destroy();
			e.send(Message.ENTITY_DIED);
			ShootEmUp.currentLevel.spawner.removeEnemy();
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	public void drop(Entity e) {
		//give player exp
		((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY)).giveExp(1);
	
		
		//create a coin
		Entity coin = new Entity();
		AnimatedGraphics coinG = new AnimatedGraphics(Art.coin, Art.base, true);
		PointSpawn coinS = new PointSpawn(coinG, new Vector2(BG.getX() + BG.getWidth() - coinG.getWidth(), BG.getY() + BG.getHeight() - coinG.getHeight()), coin);
		coin.addComponent(coinG);
		PickupCollision coinC = new PickupCollision(coin, PickupType.COIN, CoinType.ONE);
		coin.addComponent(coinS);
		coin.addComponent(coinC);
		ShootEmUp.currentLevel.newEntities.add(coin);
		
		AnimatedGraphics g;
		PointSpawn s;
		PickupCollision c;
		
		//create armour, item or weapon
		Random rand = new Random();
		int prob = rand.nextInt(3);
		if(prob == 0 ) {
			int itemProb = rand.nextInt(4);
			Entity item = new Entity();
			PickupType T = PickupType.POTION;
			if(itemProb == 0) {
				g = new AnimatedGraphics(Art.healthPotion, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
				item.addComponent(g);
				c = new PickupCollision(item, T, PotionType.HEALTH);
			} else if(itemProb == 1){
				g = new AnimatedGraphics(Art.manaPotion, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
				item.addComponent(g);
				c = new PickupCollision(item, T, PotionType.MANA);
			} else if(itemProb == 2){
				g = new AnimatedGraphics(Art.speedPotion, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
				item.addComponent(g);
				c = new PickupCollision(item, T, PotionType.SPEED);
			} else {
				g = new AnimatedGraphics(Art.knockbackPotion, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
				item.addComponent(g);
				c = new PickupCollision(item, T, PotionType.KNOCKBACK);
			}
			
			item.addComponent(s);
			item.addComponent(c);
			ShootEmUp.currentLevel.newEntities.add(item);
		} else if(prob == 1) {
			int armourProb = rand.nextInt(4);
			Entity armour = new Entity();
			PickupType T = PickupType.ARMOUR;
			if(armourProb == 0){
				g = new AnimatedGraphics(Art.shoes, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
				armour.addComponent(g);
				c = new PickupCollision(armour, T, ArmourType.BOOTS);
			} else if(armourProb == 1){
				g = new AnimatedGraphics(Art.legs, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
				armour.addComponent(g);
				c = new PickupCollision(armour, T, ArmourType.LEGS);
			} else if(armourProb == 2){
				g = new AnimatedGraphics(Art.chest, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
				armour.addComponent(g);
				c = new PickupCollision(armour, T, ArmourType.CHESTPLATE);
			} else {
				g = new AnimatedGraphics(Art.helmet, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
				armour.addComponent(g);
				c = new PickupCollision(armour, T, ArmourType.HELMET);
			}
			armour.addComponent(g);
			armour.addComponent(c);
			ShootEmUp.currentLevel.newEntities.add(armour);
		} else if(prob == 2) {	
			int weaponProb = rand.nextInt(8);
			Entity weapon = new Entity();
			if(weaponProb == 0){
				g = new AnimatedGraphics(Art.sword, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.SWORD);
			} else if(weaponProb == 0){
				g = new AnimatedGraphics(Art.battleaxe, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.BATTLEAXE);
			} else if(weaponProb == 0){
				g = new AnimatedGraphics(Art.mace, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.WARHAMMER);
			} else if(weaponProb == 0){
				g = new AnimatedGraphics(Art.bow, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.BOW);
			} else if(weaponProb == 0){
				g = new AnimatedGraphics(Art.crossbow, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.CROSSBOW);
			} else if(weaponProb == 0){
				g = new AnimatedGraphics(Art.fireStaff, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.FIRE_STAFF);
			} else if(weaponProb == 0){
				g = new AnimatedGraphics(Art.iceStaff, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.ICE_STAFF);
			} else {
				g = new AnimatedGraphics(Art.earthStaff, Art.base, true);
				s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
				weapon.addComponent(g);
				c = new PickupCollision(weapon, PickupType.WEAPON, WeaponType.GROUND_STAFF);
			}
			weapon.addComponent(s);
			weapon.addComponent(c);
			ShootEmUp.currentLevel.newEntities.add(weapon);
		}
	}
}

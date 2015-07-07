package Components.Attack;

import java.util.Random;

import Components.ComponentType;
import Components.Message;
import Components.Collision.PickupCollision;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Spawn.BaseSpawn;
import Components.Spawn.PointSpawn;
import Components.Inventory.TypeArmour;
import Components.Inventory.TypeCoin;
import Components.Inventory.TypePickup;
import Components.Inventory.PlayerInventory;
import Components.Inventory.TypePotion;
import Components.Inventory.TypeWeapon;
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
		healthRegen();
	}
	
	@Override
	public void damage(int damage, Entity e) {
		this.health -= damage;
		if(health <= 0) {
			e.destroy();
			e.send(Message.ENTITY_DIED);
			ShootEmUp.currentLevel.spawner.removeEnemy();
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}

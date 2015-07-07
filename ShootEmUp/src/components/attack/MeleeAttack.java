package components.attack;

import java.util.Random;

import object.Entity;
import object.Weapon;
import main.ShootEmUp;
import math.Vector2;
import components.ComponentType;
import components.Message;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.PlayerInventory;
import components.inventory.TypeArmour;
import components.inventory.TypeCoin;
import components.inventory.TypePickup;
import components.inventory.TypePotion;
import components.inventory.TypeWeapon;
import components.spawn.BaseSpawn;
import components.spawn.PointSpawn;
import display.Art;

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

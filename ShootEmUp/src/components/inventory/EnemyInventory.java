package components.inventory;

import java.util.Random;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.spawn.PointSpawn;
import display.Art;

public class EnemyInventory extends BasicInventory{
	
	BaseGraphics BG;
	
	public EnemyInventory(BaseGraphics BG, int level) {
		super(level);
		this.BG = BG;
	}
	
	@Override
	public void destroy(Entity e){
		drop(e);
	}

	public void drop(Entity e) {
		
		//give player exp
		((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).giveExp(1);
	
		
		//create a coin
		Entity coin = new Entity();
		AnimatedGraphics coinG = new AnimatedGraphics(Art.coin, Art.base, true);
		PointSpawn coinS = new PointSpawn(coinG, new Vector2(BG.getX() + BG.getWidth() - coinG.getWidth(), BG.getY() + BG.getHeight() - coinG.getHeight()), coin);
		coin.addComponent(coinG);
		PickupCollision coinC = new PickupCollision(coin, TypePickup.COIN, TypeCoin.ONE);
		coin.addComponent(coinS);
		coin.addComponent(coinC);
		ShootEmUp.currentLevel.newEntities.add(coin);
		
		AnimatedGraphics g;
		PointSpawn s;
		PickupCollision c;
		
		//create armour, item or weapon
		Random rand = new Random();
		TypePickup T;
		
		switch(rand.nextInt()){
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				int itemProb = rand.nextInt(4);
				Entity item = new Entity();
				T = TypePickup.POTION;
				if(itemProb == 0) {
					g = new AnimatedGraphics(Art.healthPotion, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
					item.addComponent(g);
					c = new PickupCollision(item, T, TypePotion.HEALTH);
				} else if(itemProb == 1){
					g = new AnimatedGraphics(Art.manaPotion, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
					item.addComponent(g);
					c = new PickupCollision(item, T, TypePotion.MANA);
				} else if(itemProb == 2){
					g = new AnimatedGraphics(Art.speedPotion, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
					item.addComponent(g);
					c = new PickupCollision(item, T, TypePotion.SPEED);
				} else {
					g = new AnimatedGraphics(Art.knockbackPotion, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX() + BG.getWidth() - g.getWidth(),BG.getY()), item);
					item.addComponent(g);
					c = new PickupCollision(item, T, TypePotion.KNOCKBACK);
				}
				
				item.addComponent(s);
				item.addComponent(c);
				ShootEmUp.currentLevel.newEntities.add(item);
				break;
			case 6:
			case 7:
			case 8:
				int armourProb = rand.nextInt(4);
				Entity armour = new Entity();
				T = TypePickup.ARMOUR;
				if(armourProb == 0){
					g = new AnimatedGraphics(Art.shoes, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
					armour.addComponent(g);
					c = new PickupCollision(armour, T, TypeArmour.BOOTS);
				} else if(armourProb == 1){
					g = new AnimatedGraphics(Art.legs, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
					armour.addComponent(g);
					c = new PickupCollision(armour, T, TypeArmour.LEGS);
				} else if(armourProb == 2){
					g = new AnimatedGraphics(Art.chest, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
					armour.addComponent(g);
					c = new PickupCollision(armour, T, TypeArmour.CHESTPLATE);
				} else {
					g = new AnimatedGraphics(Art.helmet, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY()), armour);
					armour.addComponent(g);
					c = new PickupCollision(armour, T, TypeArmour.HELMET);
				}
				armour.addComponent(g);
				armour.addComponent(c);
				ShootEmUp.currentLevel.newEntities.add(armour);
				break; 
			case 9:
			case 10:
				int weaponProb = rand.nextInt(8);
				T = TypePickup.WEAPON;
				Entity weapon = new Entity();
				if(weaponProb == 0){
					g = new AnimatedGraphics(Art.sword, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.SWORD);
				} else if(weaponProb == 1){
					g = new AnimatedGraphics(Art.battleaxe, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.BATTLEAXE);
				} else if(weaponProb == 2){
					g = new AnimatedGraphics(Art.mace, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.MACE);
				} else if(weaponProb == 3){
					g = new AnimatedGraphics(Art.bow, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.BOW);
				} else if(weaponProb == 4){
					g = new AnimatedGraphics(Art.crossbow, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.CROSSBOW);
				} else if(weaponProb == 5){
					g = new AnimatedGraphics(Art.fireStaff, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.FIRE_STAFF);
				} else if(weaponProb == 6){
					g = new AnimatedGraphics(Art.iceStaff, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.ICE_STAFF);
				} else {
					g = new AnimatedGraphics(Art.earthStaff, Art.base, true);
					s = new PointSpawn(g, new Vector2(BG.getX(), BG.getY() + BG.getHeight() - g.getHeight()), weapon);
					weapon.addComponent(g);
					c = new PickupCollision(weapon, T, TypeWeapon.EARTH_STAFF);
				}
				weapon.addComponent(s);
				weapon.addComponent(c);
				ShootEmUp.currentLevel.newEntities.add(weapon);
		}
	}
}

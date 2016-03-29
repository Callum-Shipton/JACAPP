package components.inventory;

import java.util.Random;

import object.Entity;
import main.ShootEmUp;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.BaseGraphics;

public class EnemyInventory extends BasicInventory{
	
	BaseGraphics BG;
	BaseAttack BA;
	
	public EnemyInventory(BaseGraphics BG, BaseAttack BA, int level) {
		super(level);
		this.BG = BG;
		this.BA = BA;
	}
	
	@Override
	public void destroy(Entity e){
		drop(e);
	}

	public void drop(Entity e) {
		
		//give player exp
		((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).giveExp(1);
	
		
		//create a coin
		PickupBuilder.buildPickup(TypePickup.COIN, TypeCoin.ONE, null, BG.getX() + BG.getWidth(), BG.getY() + BG.getHeight());
		
		//create armour, item or weapon
		Random rand = new Random();
		
		switch(rand.nextInt(3)){
		case 0:
			PickupBuilder.buildPickup(TypePickup.WEAPON, BA.getWeapon().getType(), BA.getWeapon().getSubType(), BG.getX(), BG.getY() + BG.getHeight());
			break;
		case 1:
			PickupBuilder.buildPickup(TypePickup.ARMOUR, TypeArmour.BOOTS, SubTypeArmour.LEATHER, BG.getX(), BG.getY() + BG.getHeight());
			break;
		case 2:
			PickupBuilder.buildPickup(TypePickup.POTION, TypePotion.HEALTH, null, BG.getX(), BG.getY() + BG.getHeight());
			break;
		}
	}
}

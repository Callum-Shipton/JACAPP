package Components.Inventory;

import java.util.HashSet;

import Components.Attack.PlayerAttack;
import Display.Art;
import Object.Armour;
import Object.InventoryItem;
import Object.Weapon;

public class PlayerInventory extends BasicInventory{
	
	private final int MAX_LEVEL = 99;
	private final int MAX_EXP_BOUND = 18;
	private int expBound;
	
	private HashSet<InventoryItem> inventory;
	
	private PlayerAttack PA;
	
	public PlayerInventory(PlayerAttack PA, int level, int expBound) {
		super(level);
		this.PA = PA;
		this.expBound = expBound;
		inventory = new HashSet<InventoryItem>();
	}
	
	public void giveItem(PickupType type, Subtype subtype){
		switch(type){
		case COIN: 
			if(coins < 99){
				coins++;
			}
			break;
		case POTION:
			PotionType potionType = (PotionType) subtype;
			switch(potionType){
			case HEALTH:
				PA.addHealth(5);
				break;
			case MANA:
				PA.addMana(5);
				break;
			case SPEED:
				break;
			case KNOCKBACK:
			}
			break;
		case ARMOUR:
			ArmourType armourType = (ArmourType) subtype;
			switch(armourType){
			case BOOTS:
				inventory.add(new Armour(armourType, 2, Art.shoes));
				break;
			case LEGS:
				inventory.add(new Armour(armourType, 5, Art.legs));
				break;
			case CHESTPLATE:
				inventory.add(new Armour(armourType, 10, Art.chest));
				break;
			case HELMET:
				inventory.add(new Armour(armourType, 7, Art.helmet));
			}
			break;
		case WEAPON:
			WeaponType weaponType = (WeaponType) subtype;
			switch(weaponType){
			case SWORD:
				inventory.add(new Weapon(weaponType, 3, 3, 3, true, 1, 0, Art.bow));
				break;
			case BATTLEAXE:
				inventory.add(new Weapon(weaponType, 5, 2, 2, true, 2, 0, Art.bow));
				break;
			case WARHAMMER:
				inventory.add(new Weapon(weaponType, 10, 2, 1, true, 3, 0, Art.bow));
				break;
			case CROSSBOW:
				inventory.add(new Weapon(weaponType, 10, 3, 2, false, 1, 0, Art.bow));
				break;
			case BOW:
				inventory.add(new Weapon(weaponType, 5, 2, 3, false, 1, 0, Art.bow));
			}
		}
	}
	
	public void giveExp(int exp){
		this.exp += exp;
		if(this.exp > expBound){
			if(level < MAX_LEVEL){
				this.exp = 0;
				level++;
				if (expBound < MAX_EXP_BOUND){
					expBound++;
				}		
			}
		}
	}
	
	public int getExpBound() {
		return expBound;
	}

	public void setExpBound(int expBound) {
		this.expBound = expBound;
	}
}

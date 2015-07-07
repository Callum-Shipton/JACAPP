package Components.Inventory;

import java.util.ArrayList;

import Components.Attack.PlayerAttack;
import Components.Movement.BaseMovement;
import Display.Art;
import Object.Armour;
import Object.InventoryItem;
import Object.Weapon;

public class PlayerInventory extends BasicInventory{
	
	private final int MAX_LEVEL = 99;
	private final int MAX_EXP_BOUND = 18;
	private int expBound;
	
	private ArrayList<InventoryItem> inventory;
	private ArrayList<PotionType> potions; 
	private int maxPotions = 3;
	
	protected Armour boots = null;
	protected Armour legs = null;
	protected Armour chest = null;
	protected Armour helmet = null;
	
	private PlayerAttack PA;
	private BaseMovement BM;
	
	public PlayerInventory(PlayerAttack PA, BaseMovement BM, int level, int expBound) {
		super(level);
		this.PA = PA;
		this.BM = BM;
		this.expBound = expBound;
		inventory = new ArrayList<InventoryItem>();
		potions = new ArrayList<PotionType>();
	}
	
	public void equipItem(int itemNo){
		InventoryItem item = inventory.get(itemNo);
		inventory.remove(itemNo);
		if(item instanceof Armour){
			switch(((Armour) item).getType()){
			case BOOTS:
				boots = (Armour)item;
				break;
			case LEGS:
				legs = (Armour)item;
				break;
			case CHESTPLATE:
				chest = (Armour)item;
				break;
			case HELMET:
				helmet = (Armour)item;
			}
		} else {
			PA.setWeapon((Weapon)item);
		}
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
			if(potions.size() < maxPotions){
				potions.add(potionType);
			} else {
				switch(potionType){
				case HEALTH:
					PA.addHealth(5);
					break;
				case MANA:
					PA.addMana(5);
					break;
				case SPEED:
					BM.increaseSpeed(2);
					break;
				case KNOCKBACK:
				}
			}
			break;
		case ARMOUR:
			ArmourType armourType = (ArmourType) subtype;
			switch(armourType){
			case BOOTS:
				inventory.add(new Armour(armourType, 2, Art.bootsButton));
				break;
			case LEGS:
				inventory.add(new Armour(armourType, 5, Art.legsButton));
				break;
			case CHESTPLATE:
				inventory.add(new Armour(armourType, 10, Art.chestButton));
				break;
			case HELMET:
				inventory.add(new Armour(armourType, 7, Art.helmetButton));
			}
			break;
		case WEAPON:
			WeaponType weaponType = (WeaponType) subtype;
			switch(weaponType){
			case SWORD:
				inventory.add(new Weapon(weaponType, 3, 3, 3, true, 1, 0, Art.swordProjectile, Art.swordButton));
				break;
			case BATTLEAXE:
				inventory.add(new Weapon(weaponType, 5, 2, 2, true, 2, 0, Art.swordProjectile, Art.battleaxeButton));
				break;
			case MACE:
				inventory.add(new Weapon(weaponType, 10, 2, 1, true, 3, 0, Art.swordProjectile, Art.maceButton));
				break;
			case CROSSBOW:
				inventory.add(new Weapon(weaponType, 10, 3, 2, false, 1, 0, Art.arrow, Art.crossbowButton));
				break;
			case BOW:
				inventory.add(new Weapon(weaponType, 5, 2, 3, false, 1, 0, Art.arrow, Art.bowButton));
				break;
			case FIRE_STAFF:
				inventory.add(new Weapon(weaponType, 3, 3, 3, false, 1, 0, Art.fireMagic, Art.fireStaffButton));
				break;
			case ICE_STAFF:
				inventory.add(new Weapon(weaponType, 3, 3, 3, false, 1, 0, Art.iceMagic, Art.iceStaffButton));
				break;
			case EARTH_STAFF:
				inventory.add(new Weapon(weaponType, 5, 3, 2, false, 2, 0, Art.earthMagic, Art.earthStaffButton));
				break;
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
	
	public ArrayList<InventoryItem> getInventory(){
		return inventory;
	}
	
	public Armour setBoots(){
		return boots;
	}
	
	public Armour setLegs(){
		return legs;
	}
	
	public Armour setChest(){
		return chest;
	}
	
	public Armour setHelmet(){
		return helmet;
	}
}

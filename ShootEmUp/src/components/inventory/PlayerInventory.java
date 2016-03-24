package components.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import object.Armour;
import object.ArmourBuilder;
import object.InventoryItem;
import object.Weapon;
import object.WeaponBuilder;
import save.Save;
import components.attack.PlayerAttack;
import components.movement.BaseMovement;

import static components.inventory.TypePotion.*;

public class PlayerInventory extends BasicInventory {

	private final int MAX_LEVEL = 99;
	private final int MAX_EXP_BOUND = 18;
	private int expBound;
	private int levelPoints = 0;

	private ArrayList<InventoryItem> inventory;
	private int inventorySize = 5;
	private HashMap<TypePotion,Potion> potions;
	private int maxPotions = 5;

	private PlayerAttack PA;
	private BaseMovement BM;

	private boolean speedOn;

	public PlayerInventory(PlayerAttack PA, BaseMovement BM, int level, int expBound) {
		super(level);
		this.PA = PA;
		this.BM = BM;
		this.expBound = expBound;
		inventory = new ArrayList<InventoryItem>();
		potions = new HashMap<TypePotion,Potion>();
	}
	
	public PlayerInventory(PlayerAttack PA, BaseMovement BM, int level, int expBound, Save save) {
		super(level);
		this.PA = PA;
		this.BM = BM;
		this.expBound = expBound;
		inventory = new ArrayList<InventoryItem>();
		for(TypeWeapon typeWeapon : save.getWeapons()){
			inventory.add(WeaponBuilder.buildWeapon(typeWeapon, 0));
		}
		for(TypeArmour typeArmour : save.getArmour()){
			inventory.add(ArmourBuilder.buildArmour(typeArmour));
		}
		inventorySize = save.getInventorySize();
		potions = save.getPotions();
		maxPotions = save.getMaxPotions();
		exp = save.getExp();
		coins = save.getCoins();
	}
	
	public void spendLevelPoints(int points){
		levelPoints -= points;
	}


	public void equipItem(int itemNo) {
		InventoryItem item = inventory.get(itemNo);
		InventoryItem equipped = null;
		inventory.remove(itemNo);
		if (item instanceof Armour) {
			switch (((Armour) item).getType()) {
			case BOOTS:
				equipped = PA.getBoots();
				PA.setBoots((Armour) item);
				break;
			case LEGS:
				equipped = PA.getLegs();
				PA.setLegs((Armour) item);
				break;
			case CHESTPLATE:
				equipped = PA.getChest();
				PA.setChest((Armour) item);
				break;
			case HELMET:
				equipped = PA.getHelmet();
				PA.setHelmet((Armour) item);
			}
		} else {
			equipped = PA.getWeapon();
			PA.setWeapon((Weapon) item);
		}
		if(equipped != null){
			inventory.add(equipped);
		}
	}

	public boolean giveItem(TypePickup type, Subtype subtype) {
		switch (type) {
		case COIN:
			if (coins < 99) {
				coins++;
				return true;
			}
			break;
		case POTION:
			TypePotion potionType = (TypePotion) subtype;
			if (getNumPotions() < maxPotions) {
				if(potions.containsKey(potionType)){
					potions.get(potionType).addPotion();
				}
				else{
					switch(potionType){
					case HEALTH: potions.put(HEALTH, new OneTimePotion(HEALTH));
					break;
					case MANA: potions.put(MANA, new OneTimePotion(MANA));
					break;
					case SPEED: potions.put(SPEED, new DurationPotion(SPEED,30));
					break;
					case KNOCKBACK: potions.put(KNOCKBACK, new DurationPotion(KNOCKBACK,30));
					}
				}
				return true;
			} 
			break;
		case ARMOUR:
			if(inventory.size() < inventorySize){
				TypeArmour armourType = (TypeArmour) subtype;
				inventory.add(ArmourBuilder.buildArmour(armourType));
				return true;
			}
			break;
		case WEAPON:
			if(inventory.size() < inventorySize){
				TypeWeapon weaponType = (TypeWeapon) subtype;
				inventory.add(WeaponBuilder.buildWeapon(weaponType, 0));
				return true;
			}
		}
		return false;
	}

	public void giveExp(int exp) {
		this.exp += exp;
		if (this.exp > expBound) {
			if (level < MAX_LEVEL) {
				this.exp = 0;
				level++;
				levelPoints++;
				if (expBound < MAX_EXP_BOUND) {
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

	public ArrayList<InventoryItem> getInventory() {
		return inventory;
	}
	
	public int getLevelPoints(){
		return levelPoints;
	}
	
	public int getNumPotion(TypePotion type){
		return potions.get(type).quantity;
	}
	
	public int getNumPotions(){
		int sum = 0;
		for(TypePotion type: TypePotion.values()){
			sum += getNumPotion(type);
		}
		return sum;
	}
	
	public int getInventorySize(){
		return inventorySize;
	}
	
	public void addInventorySize(int addition){
		inventorySize += addition;
	}
	
	public void addMaxPotions(int addition){
		maxPotions += addition;
	}
	
	public HashMap<TypePotion,Potion> getPotions() {
		return potions;
	}

	public int getMaxPotions() {
		return maxPotions;
	}

	public void usePotion(TypePotion type) {
		potions.get(type).usePotion();
	}
}

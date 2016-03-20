package components.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import Save.Save;
import object.Armour;
import object.InventoryItem;
import object.Weapon;
import object.WeaponBuilder;
import components.attack.PlayerAttack;
import components.movement.BaseMovement;
import display.Art;

import static components.inventory.TypePotion.*;

public class PlayerInventory extends BasicInventory {

	private final int MAX_LEVEL = 99;
	private final int MAX_EXP_BOUND = 18;
	private int expBound;
	private int levelPoints = 0;

	private ArrayList<InventoryItem> inventory;
	private int inventorySize = 5;
	private HashMap<TypePotion, Integer> potions;
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
		potions = new HashMap<TypePotion, Integer>();
		for (TypePotion type : TypePotion.values()) {
			potions.put(type, 0);
		}
	}
	
	public PlayerInventory(PlayerAttack PA, BaseMovement BM, int level, int expBound, Save save) {
		super(level);
		this.PA = PA;
		this.BM = BM;
		this.expBound = expBound;
		inventory = save.getInventory();
		inventorySize = save.getInventorySize();
		potions = save.getPotions();
		maxPotions = save.getMaxPotions();
		exp = save.getExp();
		coins = save.getCoins();
		for (TypePotion type : TypePotion.values()) {
			potions.put(type, 0);
		}
	}
	
	public void spendLevelPoints(int points){
		levelPoints -= points;
	}

	public void usePotion(TypePotion type) {
		int numPotion;
		switch (type) {
		case HEALTH:
			numPotion = potions.get(HEALTH);
			if(numPotion > 0){
				PA.addHealth(5);
				potions.replace(HEALTH, --numPotion);
			}
			break;
		case MANA:
			numPotion = potions.get(MANA);
			if(numPotion > 0){
				PA.addMana(5);
				potions.replace(MANA, --numPotion);
			}
			break;
		case SPEED:
			if (!speedOn) {
				numPotion = potions.get(SPEED);
				if(numPotion > 0){
					BM.increaseSpeed(2);
					speedOn = true;
					potions.replace(SPEED, --numPotion);
				}
			}
			break;
		case KNOCKBACK:
			numPotion = potions.get(KNOCKBACK);
			if(numPotion > 0){
				//Code for adding to knockback
				PA.setHealth(100);
				PA.setMaxHealth(100);
				PA.setMana(100);
				PA.setMaxMana(100);
				potions.replace(KNOCKBACK, --numPotion);
			}
			break;
		}
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
		inventory.add(equipped);
	}

	public void giveItem(TypePickup type, Subtype subtype) {
		switch (type) {
		case COIN:
			if (coins < 99) {
				coins++;
			}
			break;
		case POTION:
			TypePotion potionType = (TypePotion) subtype;
			if (getNumPotions() < maxPotions) {
				potions.merge(potionType, 1, (Integer a, Integer b) -> a+b);
			} else {
				switch (potionType) {
				case HEALTH:
					PA.addHealth(5);
					break;
				case MANA:
					PA.addMana(5);
					break;
				case SPEED:
					if (!speedOn) {
						BM.increaseSpeed(2);
						speedOn = true;
					}
					break;
				case KNOCKBACK:
				}
			}
			break;
		case ARMOUR:
			if(inventory.size() < inventorySize){
				TypeArmour armourType = (TypeArmour) subtype;
				switch (armourType) {
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
			}
			break;
		case WEAPON:
			if(inventory.size() < inventorySize){
				TypeWeapon weaponType = (TypeWeapon) subtype;
				inventory.add(WeaponBuilder.buildWeapon(weaponType, 0));
			}
		}
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
		return potions.get(type);
	}
	
	public int getNumPotions(){
		int sum = 0;
		for(TypePotion type: TypePotion.values()){
			sum += potions.get(type);
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
	
	public HashMap<TypePotion, Integer> getPotions() {
		return potions;
	}

	public int getMaxPotions() {
		return maxPotions;
	}
}

package components.inventory;

import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<TypePotion, Integer> potions;
	private int maxPotions = 20;

	protected Armour boots = null;
	protected Armour legs = null;
	protected Armour chest = null;
	protected Armour helmet = null;

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
				potions.replace(KNOCKBACK, --numPotion);
			}
			break;
		}
	}

	public void equipItem(int itemNo) {
		InventoryItem item = inventory.get(itemNo);
		inventory.remove(itemNo);
		if (item instanceof Armour) {
			switch (((Armour) item).getType()) {
			case BOOTS:
				boots = (Armour) item;
				break;
			case LEGS:
				legs = (Armour) item;
				break;
			case CHESTPLATE:
				chest = (Armour) item;
				break;
			case HELMET:
				helmet = (Armour) item;
			}
		} else {
			PA.setWeapon((Weapon) item);
		}
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
			if (potions.size() < maxPotions) {
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
			break;
		case WEAPON:
			TypeWeapon weaponType = (TypeWeapon) subtype;
			inventory.add(WeaponBuilder.buildWeapon(weaponType, 0));
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

	public Armour setBoots() {
		return boots;
	}

	public Armour setLegs() {
		return legs;
	}

	public Armour setChest() {
		return chest;
	}

	public Armour setHelmet() {
		return helmet;
	}
	
	public int getLevelPoints(){
		return levelPoints;
	}
}

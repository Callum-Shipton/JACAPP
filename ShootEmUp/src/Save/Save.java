package Save;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import components.ComponentType;
import components.attack.TypeAttack;
import components.inventory.TypePotion;
import main.ShootEmUp;
import object.Armour;
import object.InventoryItem;
import object.Weapon;

public class Save implements Serializable{
	
	private TypeAttack player;
	
	private int health;
	private int maxHealth;
	private int healthRegen;
	
	private int mana;
	private int maxMana;
	private int manaRegen;
	
	private int exp;
	private int playerLevel;
	
	private int level;
	private int wave;
	
	private ArrayList<InventoryItem> inventory;
	private int maxInventory;
	
	private Weapon weapon;
	private Armour helmet;
	private Armour chest;
	private Armour legs;
	private Armour boots;
	
	private HashMap<TypePotion, Integer> potions;
	private int maxPotions;
	
	public Save(){
		getData();
	}
	
	private void getData(){
		player = ((PlayerAttack) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK)).getTypeAttack;
	}

	public TypeAttack getPlayer() {
		return player;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getHealthRegen() {
		return healthRegen;
	}

	public int getMana() {
		return mana;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public int getExp() {
		return exp;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public int getLevel() {
		return level;
	}
	
	public int getWave() {
		return wave;
	}

	public ArrayList<InventoryItem> getInventory() {
		return inventory;
	}

	public int getMaxInventory() {
		return maxInventory;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Armour getHelmet() {
		return helmet;
	}

	public Armour getChest() {
		return chest;
	}

	public Armour getLegs() {
		return legs;
	}

	public Armour getBoots() {
		return boots;
	}

	public HashMap<TypePotion, Integer> getPotions() {
		return potions;
	}

	public int getMaxPotions() {
		return maxPotions;
	}
}

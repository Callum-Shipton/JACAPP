package save;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import components.ComponentType;
import components.attack.TypeAttack;
import components.attack.PlayerAttack;
import components.inventory.PlayerInventory;
import components.inventory.TypeArmour;
import components.inventory.TypePotion;
import components.inventory.TypeWeapon;
import main.ShootEmUp;
import object.InventoryItem;

public class Save implements Serializable{

	private TypeAttack player;
	
	private int lives;
	
	private int health;
	private int maxHealth;
	private int healthRegen;
	private int maxHealthRegen;

	private int mana;
	private int maxMana;
	private int manaRegen;
	private int maxManaRegen;
	
	private TypeWeapon weapon;
	private TypeArmour helmet;
	private TypeArmour chest;
	private TypeArmour legs;
	private TypeArmour boots;
	
	
	private int coins;
	private int exp;
	private int playerLevel;
	
	private ArrayList<InventoryItem> inventory;
	private int inventorySize;

	private HashMap<TypePotion, Integer> potions;
	private int maxPotions;
	
	
	private int level;
	
	
	private int wave;
	
	public Save(){
		getData();
	}
	
	private void getData(){
		
		
		
		PlayerAttack tempAttack = (PlayerAttack) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK);
		
		player = tempAttack.getTypeAttack();
		
		lives = tempAttack.getLives();
		
		health = tempAttack.getHealth();
		maxHealth = tempAttack.getMaxHealth();
		healthRegen = tempAttack.getHealthRegen();
		maxHealthRegen = tempAttack.getMaxHealthRegen();
		
		mana = tempAttack.getMana();
		maxMana = tempAttack.getMaxMana();
		manaRegen = tempAttack.getManaRegen();
		maxManaRegen = tempAttack.getMaxManaRegen();
		
		weapon = tempAttack.getWeapon().getType();
		boots = tempAttack.getBoots().getType();
		legs = tempAttack.getLegs().getType();
		chest = tempAttack.getChest().getType();
		helmet = tempAttack.getHelmet().getType();
		
		
		PlayerInventory tempInventory = (PlayerInventory) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY);
		
		coins = tempInventory.getCoins();
		exp = tempInventory.getExp();
		playerLevel = tempInventory.getLevel();
		inventory = tempInventory.getInventory();
		inventorySize = tempInventory.getInventorySize();
		potions = tempInventory.getPotions();
		maxPotions = tempInventory.getMaxPotions();
		
		
		level = ShootEmUp.currentLevel.getLevel();
		
		wave = ShootEmUp.currentLevel.getSpawner().getWave();
	}

	public int getMaxHealthRegen() {
		return maxHealthRegen;
	}

	public int getMaxManaRegen() {
		return maxManaRegen;
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

	public int getCoins() {
		return coins;
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

	public int getInventorySize() {
		return inventorySize;
	}

	public TypeWeapon getWeapon() {
		return weapon;
	}

	public TypeArmour getHelmet() {
		return helmet;
	}

	public TypeArmour getChest() {
		return chest;
	}

	public TypeArmour getLegs() {
		return legs;
	}

	public TypeArmour getBoots() {
		return boots;
	}

	public HashMap<TypePotion, Integer> getPotions() {
		return potions;
	}

	public int getMaxPotions() {
		return maxPotions;
	}
	
	public int getLives() {
		return lives;
	}
}

package save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.PlayerInventory;
import components.inventory.SubTypeWeapon;
import components.inventory.TypeArmour;
import components.inventory.TypePickup;
import components.inventory.TypePotion;
import main.ShootEmUp;
import object.Armour;
import object.InventoryItem;
import object.Potion;
import object.Weapon;

public class CharacterSave implements Serializable{

	private static final long serialVersionUID = -3277632631779431737L;
	
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
	
	private SubTypeWeapon weapon;
	private TypeArmour helmet;
	private TypeArmour chest;
	private TypeArmour legs;
	private TypeArmour boots;
	
	
	private int coins;
	private int exp;
	private int playerLevel;
	
	private ArrayList<SubTypeWeapon> weapons = new ArrayList<SubTypeWeapon>();
	private ArrayList<TypeArmour> armour = new ArrayList<TypeArmour>();;
	private int inventorySize;

	private HashMap<TypePotion, Potion> potions;
	private int maxPotions;
	
	public CharacterSave(){
		getData();
	}
	
	private void getData(){
		
		PlayerAttack tempAttack = (PlayerAttack) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK);
		
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
		
		Armour tempArmour;
		tempArmour = tempAttack.getBoots();
		if(tempArmour != null){
			boots = tempArmour.getType();
		}
		tempArmour = tempAttack.getLegs();
		if(tempArmour != null){
			legs = tempArmour.getType();
		}
		tempArmour = tempAttack.getChest();
		if(tempArmour != null){
			chest = tempArmour.getType();
		}
		tempArmour = tempAttack.getHelmet();
		if(tempArmour != null){
			helmet = tempArmour.getType();
		}
		
		
		PlayerInventory tempInventory = (PlayerInventory) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY);
		
		coins = tempInventory.getCoins();
		exp = tempInventory.getExp();
		playerLevel = tempInventory.getLevel();
		ArrayList<InventoryItem> inventory = tempInventory.getInventory();
		for(InventoryItem inventoryItem : inventory){
			if(inventoryItem.getTypePickup() == TypePickup.WEAPON){
				weapons.add(((Weapon)inventoryItem).getType());
			} else {
				armour.add(((Armour)inventoryItem).getType());
			}
		}
		inventorySize = tempInventory.getInventorySize();
		potions = tempInventory.getPotions();
		maxPotions = tempInventory.getMaxPotions();
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

	public ArrayList<SubTypeWeapon> getWeapons() {
		return weapons;
	}
	
	public ArrayList<TypeArmour> getArmour() {
		return armour;
	}

	public int getInventorySize() {
		return inventorySize;
	}

	public SubTypeWeapon getWeapon() {
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

	public HashMap<TypePotion, Potion> getPotions() {
		return potions;
	}

	public int getMaxPotions() {
		return maxPotions;
	}
	
	public int getLives() {
		return lives;
	}
}

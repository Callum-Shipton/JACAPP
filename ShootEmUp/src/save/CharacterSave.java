package save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import main.ShootEmUp;
import object.Armour;
import object.InventoryItem;
import object.Potion;
import object.Weapon;

public class CharacterSave implements Serializable {

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

	private Weapon weapon;
	private Armour helmet;
	private Armour chest;
	private Armour legs;
	private Armour boots;

	private int coins;
	private int exp;
	private int playerLevel;

	private ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
	private int inventorySize;

	private HashMap<TypePotion, Potion> potions;
	private int maxPotions;

	public CharacterSave() {
		getData();
	}

	private void getData() {

		PlayerAttack tempAttack = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK);

		player = tempAttack.getAttackType();

		lives = tempAttack.getLives();

		health = tempAttack.getHealth();
		maxHealth = tempAttack.getMaxHealth();
		healthRegen = tempAttack.getHealthRegen();
		maxHealthRegen = tempAttack.getMaxHealthRegen();

		mana = tempAttack.getMana();
		maxMana = tempAttack.getMaxMana();
		manaRegen = tempAttack.getManaRegen();
		maxManaRegen = tempAttack.getMaxManaRegen();

		weapon = tempAttack.getWeapon();
		boots = tempAttack.getBoots();
		legs = tempAttack.getLegs();
		chest = tempAttack.getChest();
		helmet = tempAttack.getHelmet();

		BaseInventory tempInventory = ShootEmUp.currentLevel.getPlayer()
				.getComponent(TypeComponent.INVENTORY);

		coins = tempInventory.getCoins();
		exp = tempInventory.getExp();
		playerLevel = tempInventory.getLevel();
		inventory = tempInventory.getInventory();
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

	public ArrayList<InventoryItem> getInventory() {
		return inventory;
	}

	public int getInventorySize() {
		return inventorySize;
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

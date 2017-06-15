package save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import main.Loop;
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

	private List<InventoryItem<?>> inventory = new ArrayList<>();
	private int inventorySize;

	private Map<TypePotion, Potion> potions;
	private int maxPotions;

	public CharacterSave() {
		getData();
	}

	private void getData() {

		PlayerAttack tempAttack = Loop.getPlayer().getComponent(TypeComponent.ATTACK);

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

		BaseInventory tempInventory = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);

		coins = tempInventory.getCoins();
		exp = tempInventory.getExp();
		playerLevel = tempInventory.getLevel();
		inventory = tempInventory.getInventory();
		inventorySize = tempInventory.getInventorySize();
		potions = tempInventory.getPotions();
		maxPotions = tempInventory.getMaxPotions();
	}

	public int getExp() {
		return exp;
	}

	public int getHealth() {
		return health;
	}

	public int getHealthRegen() {
		return healthRegen;
	}

	public Armour getHelmet() {
		return helmet;
	}

	public List<InventoryItem<?>> getInventory() {
		return inventory;
	}

	public int getInventorySize() {
		return inventorySize;
	}

	public Armour getLegs() {
		return legs;
	}

	public int getLives() {
		return lives;
	}

	public int getMana() {
		return mana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMaxHealthRegen() {
		return maxHealthRegen;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getMaxManaRegen() {
		return maxManaRegen;
	}

	public int getMaxPotions() {
		return maxPotions;
	}

	public TypeAttack getPlayer() {
		return player;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public Map<TypePotion, Potion> getPotions() {
		return potions;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	

	public Armour getBoots() {
		return boots;
	}

	public Armour getChest() {
		return chest;
	}

	public int getCoins() {
		return coins;
	}
}

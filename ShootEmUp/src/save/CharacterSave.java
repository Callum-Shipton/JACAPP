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

	private List<InventoryItem<?>> inventory = new ArrayList<>();
	private int inventorySize;

	private Map<TypePotion, Potion> potions;
	private int maxPotions;

	public CharacterSave() {
		getData();
	}

	public Armour getBoots() {
		return this.boots;
	}

	public Armour getChest() {
		return this.chest;
	}

	public int getCoins() {
		return this.coins;
	}

	private void getData() {

		PlayerAttack tempAttack = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);

		this.player = tempAttack.getAttackType();

		this.lives = tempAttack.getLives();

		this.health = tempAttack.getHealth();
		this.maxHealth = tempAttack.getMaxHealth();
		this.healthRegen = tempAttack.getHealthRegen();
		this.maxHealthRegen = tempAttack.getMaxHealthRegen();

		this.mana = tempAttack.getMana();
		this.maxMana = tempAttack.getMaxMana();
		this.manaRegen = tempAttack.getManaRegen();
		this.maxManaRegen = tempAttack.getMaxManaRegen();

		this.weapon = tempAttack.getWeapon();
		this.boots = tempAttack.getBoots();
		this.legs = tempAttack.getLegs();
		this.chest = tempAttack.getChest();
		this.helmet = tempAttack.getHelmet();

		BaseInventory tempInventory = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);

		this.coins = tempInventory.getCoins();
		this.exp = tempInventory.getExp();
		this.playerLevel = tempInventory.getLevel();
		this.inventory = tempInventory.getInventory();
		this.inventorySize = tempInventory.getInventorySize();
		this.potions = tempInventory.getPotions();
		this.maxPotions = tempInventory.getMaxPotions();
	}

	public int getExp() {
		return this.exp;
	}

	public int getHealth() {
		return this.health;
	}

	public int getHealthRegen() {
		return this.healthRegen;
	}

	public Armour getHelmet() {
		return this.helmet;
	}

	public List<InventoryItem<?>> getInventory() {
		return this.inventory;
	}

	public int getInventorySize() {
		return this.inventorySize;
	}

	public Armour getLegs() {
		return this.legs;
	}

	public int getLives() {
		return this.lives;
	}

	public int getMana() {
		return this.mana;
	}

	public int getManaRegen() {
		return this.manaRegen;
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public int getMaxHealthRegen() {
		return this.maxHealthRegen;
	}

	public int getMaxMana() {
		return this.maxMana;
	}

	public int getMaxManaRegen() {
		return this.maxManaRegen;
	}

	public int getMaxPotions() {
		return this.maxPotions;
	}

	public TypeAttack getPlayer() {
		return this.player;
	}

	public int getPlayerLevel() {
		return this.playerLevel;
	}

	public Map<TypePotion, Potion> getPotions() {
		return this.potions;
	}

	public Weapon getWeapon() {
		return this.weapon;
	}
}

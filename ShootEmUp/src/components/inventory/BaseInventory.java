package components.inventory;

import static components.inventory.TypePotion.HEALTH;
import static components.inventory.TypePotion.KNOCKBACK;
import static components.inventory.TypePotion.MANA;
import static components.inventory.TypePotion.SPEED;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import components.Component;
import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.spawn.PointSpawn;
import display.Art;
import main.ShootEmUp;
import math.Vector2;
import object.Armour;
import object.DurationPotion;
import object.Entity;
import object.InventoryItem;
import object.OneTimePotion;
import object.Potion;
import object.Weapon;
import save.CharacterSave;

public class BaseInventory extends Component implements InventoryComponent {

	protected TypeComponent type = TypeComponent.INVENTORY;

	protected final String BOW = "BOW";
	protected final String DAGGER = "DAGGER";
	protected final String ONE_HANDED = "ONE_HANDED";
	protected final String TWO_HANDED = "TWO_HANDED";
	protected final String STAFF = "STAFF";
	protected final String CROSSBOW = "CROSSBOW";

	protected int coins;

	protected final int MAX_LEVEL = 99;
	protected int level;

	protected int exp;
	protected int expBound;
	protected int levelPoints = 0;

	protected ArrayList<InventoryItem> inventory;
	protected int inventorySize = 5;
	protected Map<TypePotion, Potion> potions = new EnumMap<TypePotion, Potion>(TypePotion.class); // NOSONAR
	protected int maxPotions = 5;

	protected String[] weaponTypes = new String[2];

	protected BaseAttack BA;
	protected BaseGraphics BG;

	public BaseInventory(BaseGraphics BG, BaseAttack BA, int level) {
		this.BG = BG;
		this.BA = BA;
		this.level = level;
		this.expBound = level + 1;
		this.inventory = new ArrayList<InventoryItem>();
		switch (BA.getAttackType()) {
		case ARCHER:
			this.weaponTypes[0] = BOW;
			this.weaponTypes[1] = DAGGER;
			break;
		case MAGE:
			this.weaponTypes[0] = STAFF;
			this.weaponTypes[1] = DAGGER;
			break;
		case WARRIOR:
			this.weaponTypes[0] = ONE_HANDED;
			this.weaponTypes[1] = TWO_HANDED;
			break;
		case BATTLE_MAGE:
			this.weaponTypes[0] = ONE_HANDED;
			this.weaponTypes[1] = STAFF;
			break;
		case ROGUE:
			this.weaponTypes[0] = CROSSBOW;
			this.weaponTypes[1] = DAGGER;
			break;
		default:
			this.weaponTypes[0] = ONE_HANDED;
			this.weaponTypes[1] = TWO_HANDED;
		}
	}

	public BaseInventory(BaseGraphics BG, PlayerAttack BA, CharacterSave save) {
		this(BG, BA, save.getPlayerLevel());
		this.inventory = save.getInventory();
		this.inventorySize = save.getInventorySize();
		this.potions = save.getPotions();
		this.maxPotions = save.getMaxPotions();
		this.exp = save.getExp();
		this.coins = save.getCoins();
	}

	public void addInventorySize(int addition) {
		this.inventorySize += addition;
	}

	public void addMaxPotions(int addition) {
		this.maxPotions += addition;
	}

	@Override
	public void destroy(Entity e) {
		drop(e);
	}

	private void drop(Entity e) {

		// give player exp
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		BI.giveExp(1);

		dropCoin();

		// create armour, item or weapon
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			this.BA.getWeapon().destroy(e);
			break;
		case 1:
			switch (rand.nextInt(4)) {
			case 0:
				if (this.BA.getHelmet() != null) {
					this.BA.getHelmet().destroy(e);
				}
				break;
			case 1:
				if (this.BA.getChest() != null) {
					this.BA.getChest().destroy(e);
				}
				break;
			case 2:
				if (this.BA.getLegs() != null) {
					this.BA.getLegs().destroy(e);
				}
				break;
			case 3:
				if (this.BA.getBoots() != null) {
					this.BA.getBoots().destroy(e);
				}
				break;
			default:
			}
			break;
		case 2:
			switch (rand.nextInt(4)) {
			case 0:
				new OneTimePotion("Health").destroy(e);
				break;
			case 1:
				new OneTimePotion("Mana").destroy(e);
				break;
			case 2:
				new DurationPotion("Speed", 30).destroy(e);
				break;
			case 3:
				new DurationPotion("Knockback", 30).destroy(e);
				break;
			default:
			}
		default:
		}

	}

	private void dropCoin() {
		Entity item = new Entity();

		AnimatedGraphics CoinG = null;
		PointSpawn CoinS;
		PickupCollision CoinC;

		CoinG = new AnimatedGraphics(Art.getImage("Coin"), Art.base, true, 1f);

		CoinS = new PointSpawn(CoinG,
				new Vector2(this.BG.getX() - this.BG.getWidth(), this.BG.getY() - this.BG.getHeight()), item);
		item.addComponent(CoinG);
		CoinC = new PickupCollision(item, TypePickup.COIN, "Coin");
		item.addComponent(CoinS);
		item.addComponent(CoinC);
		ShootEmUp.getCurrentLevel().addEntity(item);
	}

	public void equipItem(int itemNo) {
		InventoryItem item = this.inventory.get(itemNo);
		InventoryItem equipped = null;
		this.inventory.remove(itemNo);
		if (item instanceof Armour) {
			switch (((Armour) item).getType()) {
			case "BOOTS":
				equipped = this.BA.getBoots();
				this.BA.setBoots((Armour) item);
				break;
			case "LEGS":
				equipped = this.BA.getLegs();
				this.BA.setLegs((Armour) item);
				break;
			case "CHEST":
				equipped = this.BA.getChest();
				this.BA.setChest((Armour) item);
				break;
			case "HELMET":
				equipped = this.BA.getHelmet();
				this.BA.setHelmet((Armour) item);
				break;
			default:
			}
		} else {
			equipped = this.BA.getWeapon();
			if ((((Weapon) item).getType() == this.weaponTypes[0])
					|| (((Weapon) item).getType() == this.weaponTypes[1])) {
				this.BA.setWeapon((Weapon) item);
			} else {
				this.inventory.add(item);
				equipped = null;
			}
		}
		if (equipped != null) {
			this.inventory.add(equipped);
		}
	}

	public int getCoins() {
		return this.coins;
	}

	public int getExp() {
		return this.exp;
	}

	public int getExpBound() {
		return this.expBound;
	}

	public ArrayList<InventoryItem> getInventory() {
		return this.inventory;
	}

	public int getInventorySize() {
		return this.inventorySize;
	}

	public int getLevel() {
		return this.level;
	}

	public int getLevelPoints() {
		return this.levelPoints;
	}

	public int getMaxPotions() {
		return this.maxPotions;
	}

	public int getNumPotion(TypePotion type) {
		if (this.potions.containsKey(type)) {
			return this.potions.get(type).getQuantity();
		}
		return 0;
	}

	public int getNumPotions() {
		int sum = 0;
		for (TypePotion type : this.potions.keySet()) {
			sum += getNumPotion(type);
		}
		return sum;
	}

	public Map<TypePotion, Potion> getPotions() {
		return this.potions;
	}

	@Override
	public TypeComponent getType() {
		return this.type;
	}

	public void giveExp(int exp) {
		this.exp += exp;
		if (this.exp > this.expBound) {
			if (this.level < this.MAX_LEVEL) {
				this.exp = 0;
				this.level++;
				this.levelPoints++;
				this.expBound++;
			}
		}
	}

	public boolean giveItem(TypePickup type, String name) {
		switch (type) {
		case WEAPON:
			if (this.inventory.size() < this.inventorySize) {
				this.inventory.add(new Weapon(name, 0));
				return true;
			}
			break;
		case ARMOUR:
			if (this.inventory.size() < this.inventorySize) {
				this.inventory.add(new Armour(name));
				return true;
			}
			break;
		case COIN:
			if (this.coins < 99) {
				this.coins++;
				return true;
			}
			break;
		case POTION:
			String potionType = name;
			if (getNumPotions() < this.maxPotions) {
				if (this.potions.containsKey(potionType)) {
					this.potions.get(potionType).addPotion();
				} else {
					switch (potionType) {
					case "Health":
						this.potions.put(HEALTH, new OneTimePotion("Health"));
						break;
					case "Mana":
						this.potions.put(MANA, new OneTimePotion("Mana"));
						break;
					case "Speed":
						this.potions.put(SPEED, new DurationPotion("Speed", 30));
						break;
					case "Knockback":
						this.potions.put(KNOCKBACK, new DurationPotion("Knockback", 30));
						break;
					default:
					}
				}
				return true;
			}
		default:
		}
		return false;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setExpBound(int expBound) {
		this.expBound = expBound;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setType(TypeComponent type) {
		this.type = type;
	}

	public void spendCoins(int coins) {
		this.coins -= coins;
	}

	public void spendLevelPoints(int points) {
		this.levelPoints -= points;
	}

	@Override
	public void update(Entity e) {
		for (Potion potion : this.potions.values()) {
			potion.update(e);
		}
	}

	public void usePotion(TypePotion type) {
		if (this.potions.containsKey(type))
			this.potions.get(type).usePotion();
	}
}

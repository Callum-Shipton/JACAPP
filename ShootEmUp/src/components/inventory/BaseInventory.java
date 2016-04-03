package components.inventory;

import static components.inventory.TypePotion.HEALTH;
import static components.inventory.TypePotion.KNOCKBACK;
import static components.inventory.TypePotion.MANA;
import static components.inventory.TypePotion.SPEED;

import java.util.ArrayList;
import java.util.HashMap;
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

	protected int coins;

	protected final int MAX_LEVEL = 99;
	protected int level;

	protected int exp;
	protected int expBound;
	protected int levelPoints = 0;

	protected ArrayList<InventoryItem> inventory;
	protected int inventorySize = 5;
	protected HashMap<TypePotion, Potion> potions = new HashMap<TypePotion, Potion>();;
	protected int maxPotions = 5;

	protected String[] weaponTypes = new String[2];

	protected BaseAttack BA;
	protected BaseGraphics BG;

	public BaseInventory(BaseGraphics BG, BaseAttack BA, int level) {
		this.BG = BG;
		this.BA = BA;
		this.level = level;
		expBound = level + 1;
		inventory = new ArrayList<InventoryItem>();
		switch (BA.getAttackType()) {
			case ARCHER:
				weaponTypes[0] = "BOW";
				weaponTypes[1] = "DAGGER";
				break;
			case MAGE:
				weaponTypes[0] = "STAFF";
				weaponTypes[1] = "DAGGER";
				break;
			case WARRIOR:
				weaponTypes[0] = "ONE_HANDED";
				weaponTypes[1] = "TWO_HANDED";
				break;
			case BATTLE_MAGE:
				weaponTypes[0] = "ONE_HANDED";
				weaponTypes[1] = "STAFF" ;
				break;
			case ROGUE:
				weaponTypes[0] = "CROSSBOW";
				weaponTypes[1] = "DAGGER";
				break;
		}
	}

	public BaseInventory(BaseGraphics BG, PlayerAttack BA, CharacterSave save) {
		this(BG, BA, save.getPlayerLevel());
		inventory = save.getInventory();
		inventorySize = save.getInventorySize();
		potions = save.getPotions();
		maxPotions = save.getMaxPotions();
		exp = save.getExp();
		coins = save.getCoins();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void spendCoins(int coins) {
		this.coins -= coins;
	}

	public void setType(TypeComponent type) {
		this.type = type;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
	}

	@Override
	public TypeComponent getType() {
		return type;
	}

	@Override
	public void update(Entity e) {
		for (TypePotion type : potions.keySet()) {
			potions.get(type).update(e);
		}
	}

	public void spendLevelPoints(int points) {
		levelPoints -= points;
	}

	public void equipItem(int itemNo) {
		InventoryItem item = inventory.get(itemNo);
		InventoryItem equipped = null;
		inventory.remove(itemNo);
		if (item instanceof Armour) {
			switch (((Armour) item).getType()) {
				case "BOOTS":
					equipped = BA.getBoots();
					BA.setBoots((Armour) item);
					break;
				case "LEGS":
					equipped = BA.getLegs();
					BA.setLegs((Armour) item);
					break;
				case "CHEST":
					equipped = BA.getChest();
					BA.setChest((Armour) item);
					break;
				case "HELMET":
					equipped = BA.getHelmet();
					BA.setHelmet((Armour) item);
			}
		} else {
			equipped = BA.getWeapon();
			if ((((Weapon) item).getType() == weaponTypes[0]) || (((Weapon) item).getType() == weaponTypes[1])) {
				BA.setWeapon((Weapon) item);
			} else {
				inventory.add(item);
				equipped = null;
			}
		}
		if (equipped != null) {
			inventory.add(equipped);
		}
	}
	
	public boolean giveItem(TypePickup type, String name) {
		switch (type) {
			case WEAPON:
				if (inventory.size() < inventorySize) {
					inventory.add(new Weapon(name, 0));
					return true;
				}
				break;
			case ARMOUR:
				if (inventory.size() < inventorySize) {
					inventory.add(new Armour(name));
					return true;
				}
				break;
		case COIN:
			if (coins < 99) {
				coins++;
				return true;
			}
			break;
		case POTION:
			String potionType = name;
			if (getNumPotions() < maxPotions) {
				if (potions.containsKey(potionType)) {
					potions.get(potionType).addPotion();
				} else {
					switch (potionType) {
						case "Health":
							potions.put(HEALTH, new OneTimePotion("Health"));
							break;
						case "Mana":
							potions.put(MANA, new OneTimePotion("Mana"));
							break;
						case "Speed":
							potions.put(SPEED, new DurationPotion("Speed", 30));
							break;
						case "Knockback":
							potions.put(KNOCKBACK, new DurationPotion("Knockback", 30));
					}
				}
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
				expBound++;
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

	public int getLevelPoints() {
		return levelPoints;
	}

	public int getNumPotion(TypePotion type) {
		if (potions.containsKey(type)) {
			return potions.get(type).quantity;
		}
		return 0;
	}

	public int getNumPotions() {
		int sum = 0;
		for (TypePotion type : potions.keySet()) {
			sum += getNumPotion(type);
		}
		return sum;
	}

	public int getInventorySize() {
		return inventorySize;
	}

	public void addInventorySize(int addition) {
		inventorySize += addition;
	}

	public void addMaxPotions(int addition) {
		maxPotions += addition;
	}

	public HashMap<TypePotion, Potion> getPotions() {
		return potions;
	}

	public int getMaxPotions() {
		return maxPotions;
	}

	public void usePotion(TypePotion type) {
		potions.get(type).usePotion();
	}

	@Override
	public void destroy(Entity e) {
		drop(e);
	}

	public void drop(Entity e) {

		// give player exp
		BaseInventory BI = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY);
		BI.giveExp(1);

		dropCoin();

		// create armour, item or weapon
		Random rand = new Random();

		switch (rand.nextInt(3)) {
			case 0:
				BA.getWeapon().drop(BG.getX(), BG.getY());
				break;
			case 1:
				switch (rand.nextInt(4)) {
					case 0:
						if (BA.getHelmet() != null) {
							BA.getHelmet().drop(BG.getX(), BG.getY());
						}
						break;
					case 1:
						if (BA.getChest() != null) {
							BA.getChest().drop(BG.getX(), BG.getY());
						}
						break;
					case 2:
						if (BA.getLegs() != null) {
							BA.getLegs().drop(BG.getX(), BG.getY());
						}
						break;
					case 3:
						if (BA.getBoots() != null) {
							BA.getBoots().drop(BG.getX(), BG.getY());
						}
						break;
				}
				break;
			case 2:
				switch (rand.nextInt(4)) {
				case 0:
					new OneTimePotion("Health").drop(BG.getX(), BG.getY());
					break;
				case 1:
					new OneTimePotion("Mana").drop(BG.getX(), BG.getY());
					break;
				case 2:
					new DurationPotion("Speed", 30).drop(BG.getX(), BG.getY());
					break;
				case 3:
					new DurationPotion("Knockback", 30).drop(BG.getX(), BG.getY());
					break;
			}	
		}
	}
	
	private void dropCoin(){
		Entity item = new Entity();

		AnimatedGraphics CoinG = null;
		PointSpawn CoinS;
		PickupCollision CoinC;

		CoinG = new AnimatedGraphics(Art.getImage("Coin"), Art.base, true);
		
		CoinS = new PointSpawn(CoinG, new Vector2(BG.getX() - BG.getWidth(), BG.getY() - BG.getHeight()), item);
		item.addComponent(CoinG);
		CoinC = new PickupCollision(item, TypePickup.COIN, "Coin");
		item.addComponent(CoinS);
		item.addComponent(CoinC);
		ShootEmUp.currentLevel.newEntities.add(item);
	}
}

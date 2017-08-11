package components.inventory;

import static components.inventory.TypePotion.HEALTH;
import static components.inventory.TypePotion.KNOCKBACK;
import static components.inventory.TypePotion.MANA;
import static components.inventory.TypePotion.SPEED;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.audio.BaseAudio;
import components.audio.EventAudio;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.interfaces.InventoryComponent;
import components.spawn.PointSpawn;
import entity.Entity;
import main.ShootEmUp;
import object.Armour;
import object.DurationPotion;
import object.InventoryItem;
import object.OneTimePotion;
import object.Potion;
import object.Weapon;
import save.CharacterSave;

public class BaseInventory extends Component implements InventoryComponent {

	protected transient int coins;

	protected static final int MAX_LEVEL = 99;
	protected transient int level;

	protected transient int exp;
	protected transient int expBound;
	protected transient int levelPoints = 0;

	protected transient List<InventoryItem<?>> inventory = new ArrayList<>();
	protected transient int inventorySize = 5;
	protected transient Map<TypePotion, Potion> potions = new EnumMap<>(TypePotion.class); // NOSONAR
	protected transient int maxPotions = 5;

	public BaseInventory(int level) {
		this.level = level;
		expBound = level + 1;
	}

	public BaseInventory(BaseInventory baseInventory) {

	}

	public BaseInventory(CharacterSave save) {
		this(save.getPlayerLevel());
		inventory = save.getInventory();
		inventorySize = save.getInventorySize();
		potions = save.getPotions();
		maxPotions = save.getMaxPotions();
		exp = save.getExp();
		coins = save.getCoins();
	}

	public void addInventorySize(int addition) {
		inventorySize += addition;
	}

	public void addMaxPotions(int addition) {
		maxPotions += addition;
	}

	@Override
	public void destroy() {
		drop(getEntity());
	}

	private void drop(Entity e) {

		// give player exp
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		BI.giveExp(1);

		BaseAttack BA = entity.getComponent(TypeComponent.ATTACK);

		dropCoin();

		// create armour, item or weapon
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			BA.getWeapon().destroy(e);
			break;
		case 1:
			switch (rand.nextInt(4)) {
			case 0:
				if (BA.getHelmet() != null) {
					BA.getHelmet().destroy(e);
				}
				break;
			case 1:
				if (BA.getChest() != null) {
					BA.getChest().destroy(e);
				}
				break;
			case 2:
				if (BA.getLegs() != null) {
					BA.getLegs().destroy(e);
				}
				break;
			case 3:
				if (BA.getBoots() != null) {
					BA.getBoots().destroy(e);
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
				new DurationPotion("Speed", 120).destroy(e);
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

		AnimatedGraphics coinG;
		PointSpawn coinS;
		PickupCollision coinC;

		BaseGraphics BG = entity.getComponent(TypeComponent.GRAPHICS);

		coinG = new AnimatedGraphics("Coin", true, BG.getX() - BG.getWidth(), BG.getY() - BG.getHeight());

		coinS = new PointSpawn();
		coinS.setSpawnLocation(new Vector2f(BG.getX(), BG.getY()));
		item.addComponent(coinG);
		coinC = new PickupCollision(TypePickup.COIN, "Coin");
		Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);
		sounds.put(MessageId.PICKUP, "Pickup.ogg");
		BaseAudio audioComponent = new EventAudio(sounds);
		item.addComponent(coinS);
		item.addComponent(coinC);
		item.addComponent(audioComponent);
		coinS.spawn();
		ShootEmUp.getGame().getCurrentLevel().addEntity(item);
	}

	public void equipItem(int itemNo) {

		BaseAttack BA = entity.getComponent(TypeComponent.ATTACK);

		InventoryItem<?> item = inventory.get(itemNo);
		InventoryItem<?> equipped = null;
		inventory.remove(itemNo);
		if (item instanceof Armour) {
			Armour a = (Armour) item;
			switch (a.getType()) {
			case "Boots":
				equipped = BA.getBoots();
				BA.setBoots(a);
				break;
			case "Legs":
				equipped = BA.getLegs();
				BA.setLegs(a);
				break;
			case "Chest":
				equipped = BA.getChest();
				BA.setChest(a);
				break;
			case "Helmet":
				equipped = BA.getHelmet();
				BA.setHelmet(a);
				break;
			default:
			}
			if (equipped != null) {
				inventory.add(equipped);
			}
		} else if (item instanceof Weapon) {
			Weapon w = (Weapon) item;
			equipped = BA.getWeapon();
			BA.setWeapon(w);
			if (equipped != null) {
				inventory.add(equipped);
			}
		}
	}

	public int getCoins() {
		return coins;
	}

	public int getExp() {
		return exp;
	}

	public int getExpBound() {
		return expBound;
	}

	public List<InventoryItem<?>> getInventory() {
		return inventory;
	}

	public int getInventorySize() {
		return inventorySize;
	}

	public int getLevel() {
		return level;
	}

	public int getLevelPoints() {
		return levelPoints;
	}

	public int getMaxPotions() {
		return maxPotions;
	}

	public int getNumPotion(TypePotion type) {
		if (potions.containsKey(type)) {
			return potions.get(type).getQuantity();
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

	public Map<TypePotion, Potion> getPotions() {
		return potions;
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.INVENTORY;
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

	public boolean giveItem(TypePickup type, String name) {
		BaseAttack attack = entity.getComponent(TypeComponent.ATTACK);
		int team = attack.getTeam();
		switch (type) {
		case WEAPON:
			if (inventory.size() < inventorySize) {
				inventory.add(new Weapon(name, team));
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
						if (!potions.containsKey(HEALTH)) {
							potions.put(HEALTH, new OneTimePotion("Health"));
						} else {
							potions.get(HEALTH).addPotion();
						}
						break;
					case "Mana":
						if (!potions.containsKey(MANA)) {
							potions.put(MANA, new OneTimePotion("Mana"));
						} else {
							potions.get(MANA).addPotion();
						}
						break;
					case "Speed":
						if (!potions.containsKey(SPEED)) {
							potions.put(SPEED, new DurationPotion("Speed", 120));
						} else {
							potions.get(SPEED).addPotion();
						}
						break;
					case "Knockback":
						if (!potions.containsKey(KNOCKBACK)) {
							potions.put(KNOCKBACK, new DurationPotion("Knockback", 30));
						} else {
							potions.get(KNOCKBACK).addPotion();
						}
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
	public void receive(Message m) {
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

	public void spendCoins(int coins) {
		this.coins -= coins;
	}

	public void spendLevelPoints(int points) {
		levelPoints -= points;
	}

	@Override
	public void update() {
		for (Potion potion : potions.values()) {
			potion.update(getEntity());
		}
	}

	public void usePotion(TypePotion type) {
		if (potions.containsKey(type)) {
			potions.get(type).usePotion();
		}
	}
}

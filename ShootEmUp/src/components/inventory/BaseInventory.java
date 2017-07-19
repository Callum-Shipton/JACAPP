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
import components.spawn.PointSpawn;
import display.ImageProcessor;
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

	protected static final String BOW = "Bow";
	protected static final String DAGGER = "Dagger";
	protected static final String ONE_HANDED = "OneHanded";
	protected static final String TWO_HANDED = "TwoHanded";
	protected static final String STAFF = "Staff";
	protected static final String CROSSBOW = "Crossbow";

	protected int coins;

	protected static final int MAX_LEVEL = 99;
	protected int level;

	protected int exp;
	protected int expBound;
	protected int levelPoints = 0;

	protected List<InventoryItem<?>> inventory = new ArrayList<>();
	protected int inventorySize = 5;
	protected Map<TypePotion, Potion> potions = new EnumMap<>(TypePotion.class); // NOSONAR
	protected int maxPotions = 5;

	protected String[] weaponTypes = new String[2];

	protected BaseAttack attackComponent;
	protected BaseGraphics graphicsComponent;

	public BaseInventory(int level, Entity entity) {
		super(entity);

		this.graphicsComponent = entity.getComponent(TypeComponent.GRAPHICS);
		this.attackComponent = entity.getComponent(TypeComponent.ATTACK);
		this.level = level;
		expBound = level + 1;

		switch (attackComponent.getAttackType()) {
		case ARCHER:
			weaponTypes[0] = BOW;
			weaponTypes[1] = DAGGER;
			break;
		case MAGE:
			weaponTypes[0] = STAFF;
			weaponTypes[1] = DAGGER;
			break;
		case WARRIOR:
			weaponTypes[0] = ONE_HANDED;
			weaponTypes[1] = TWO_HANDED;
			break;
		case BATTLE_MAGE:
			weaponTypes[0] = ONE_HANDED;
			weaponTypes[1] = STAFF;
			break;
		case ROGUE:
			weaponTypes[0] = CROSSBOW;
			weaponTypes[1] = DAGGER;
			break;
		default:
			weaponTypes[0] = ONE_HANDED;
			weaponTypes[1] = TWO_HANDED;
		}
	}

	public BaseInventory(CharacterSave save, Entity entity) {
		this(save.getPlayerLevel(), entity);
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
	public void destroy(Entity e) {
		drop(e);
	}

	private void drop(Entity e) {

		// give player exp
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		BI.giveExp(1);

		dropCoin();

		// create armour, item or weapon
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			attackComponent.getWeapon().destroy(e);
			break;
		case 1:
			switch (rand.nextInt(4)) {
			case 0:
				if (attackComponent.getHelmet() != null) {
					attackComponent.getHelmet().destroy(e);
				}
				break;
			case 1:
				if (attackComponent.getChest() != null) {
					attackComponent.getChest().destroy(e);
				}
				break;
			case 2:
				if (attackComponent.getLegs() != null) {
					attackComponent.getLegs().destroy(e);
				}
				break;
			case 3:
				if (attackComponent.getBoots() != null) {
					attackComponent.getBoots().destroy(e);
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

		AnimatedGraphics coinG = null;
		PointSpawn coinS;
		PickupCollision coinC;

		coinG = new AnimatedGraphics(ImageProcessor.getImage("Coin"), ImageProcessor.base, true,
				graphicsComponent.getX() - graphicsComponent.getWidth(),
				graphicsComponent.getY() - graphicsComponent.getHeight(), item);

		coinS = new PointSpawn(new Vector2f(graphicsComponent.getX(), graphicsComponent.getY()), item);
		item.addComponent(coinG);
		coinC = new PickupCollision(TypePickup.COIN, "Coin", item);
		Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);
		sounds.put(MessageId.PICKUP, "Pickup.ogg");
		BaseAudio audioComponent = new EventAudio(sounds, item);
		item.addComponent(coinS);
		item.addComponent(coinC);
		item.addComponent(audioComponent);
		coinS.spawn(item);
		ShootEmUp.getGame().getCurrentLevel().addEntity(item);
	}

	public void equipItem(int itemNo) {
		InventoryItem<?> item = inventory.get(itemNo);
		InventoryItem<?> equipped = null;
		inventory.remove(itemNo);
		if (item instanceof Armour) {
			switch (((Armour) item).getType()) {
			case "Boots":
				equipped = attackComponent.getBoots();
				attackComponent.setBoots((Armour) item);
				break;
			case "Legs":
				equipped = attackComponent.getLegs();
				attackComponent.setLegs((Armour) item);
				break;
			case "Chest":
				equipped = attackComponent.getChest();
				attackComponent.setChest((Armour) item);
				break;
			case "Helmet":
				equipped = attackComponent.getHelmet();
				attackComponent.setHelmet((Armour) item);
				break;
			default:
			}
		} else {
			equipped = attackComponent.getWeapon();
			if ((((Weapon) item).getType().compareTo(weaponTypes[0]) == 0)
					|| (((Weapon) item).getType().compareTo(weaponTypes[1]) == 0)) {
				attackComponent.setWeapon((Weapon) item);
			} else {
				inventory.add(item);
				equipped = null;
			}
		}
		if (equipped != null) {
			inventory.add(equipped);
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

	public void spendCoins(int coins) {
		this.coins -= coins;
	}

	public void spendLevelPoints(int points) {
		levelPoints -= points;
	}

	@Override
	public void update(Entity e) {
		for (Potion potion : potions.values()) {
			potion.update(e);
		}
	}

	public void usePotion(TypePotion type) {
		if (potions.containsKey(type)) {
			potions.get(type).usePotion();
		}
	}
}

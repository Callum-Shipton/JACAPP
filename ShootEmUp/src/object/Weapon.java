package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Vector2f;

import com.google.gson.JsonArray;

import components.TypeComponent;
import components.collision.HitCollision;
import components.control.RangeControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.movement.FlyingMovement;
import components.spawn.PointSpawn;
import entity.Entity;
import logging.Logger;
import main.ShootEmUp;

public final class Weapon extends InventoryItem<Weapon> {

	private static final long serialVersionUID = 529276641692099199L;

	protected static String directoryPath = "res/Objects/Items/Weapons";

	private static Map<String, Map<String, Weapon>> weaponSystem = new HashMap<>();

	private int damage;
	private int range;
	private int fireRate;
	private boolean melee;
	private int manaCost;
	private transient int team;
	private String particleImage;
	private Element element;

	public Weapon(String typeName, int team) {
		if (weaponSystem.isEmpty()) {
			initSystem();
		}
		Weapon w = null;
		if (weaponSystem.containsKey(typeName)) {
			int temp = rand.nextInt(weaponSystem.get(typeName).size());
			Weapon[] typedWeapons = new Weapon[weaponSystem.get(typeName).size()];
			typedWeapons = weaponSystem.get(typeName).values().toArray(typedWeapons);
			w = typedWeapons[temp];
			this.type = typeName;

		} else {
			for (Entry<String, Map<String, Weapon>> typedWeapons : weaponSystem.entrySet()) {
				if (typedWeapons.getValue().containsKey(typeName)) {
					w = typedWeapons.getValue().get(typeName);
					this.type = typedWeapons.getKey();
				}
			}
		}

		if (w == null) {
			// Pick a random weapon
			Logger.warn("A weapon was created with unknown name/type: " + typeName);
		} else {
			this.name = w.name;
			this.damage = w.damage;
			this.range = w.range;
			this.fireRate = w.fireRate;
			this.melee = w.melee;
			this.manaCost = w.manaCost;
			this.element = w.element;
			this.team = team;
			this.particleImage = w.particleImage;
			this.typePickup = TypePickup.WEAPON;
		}
	}

	public void attack(Entity e, int direction) {
		BaseGraphics graphicsComponent = e.getComponent(TypeComponent.GRAPHICS);
		float posX = graphicsComponent.getX();
		float posY = graphicsComponent.getY();
		float width = graphicsComponent.getWidth();
		float height = graphicsComponent.getHeight();

		// create particle
		Entity particle = new Entity();
		AnimatedGraphics g = new AnimatedGraphics(getParticleImage(), false);
		g.setDirection(direction);
		particle.addComponent(g);

		switch (direction) {
		case 0:
			posX += (width - g.getWidth()) / 2;
			posY -= g.getHeight();
			break;
		case 1:
			posX += width;
			posY -= g.getHeight();
			break;
		case 2:
			posX += width;
			posY += (height - g.getHeight()) / 2;
			break;
		case 3:
			posX += width;
			posY += height;
			break;
		case 4:
			posX += (width - g.getWidth()) / 2;
			posY += height;
			break;
		case 5:
			posX -= g.getWidth();
			posY += height;
			break;
		case 6:
			posX -= g.getWidth();
			posY += (height - g.getHeight()) / 2;
			break;
		case 7:
			posX -= g.getWidth();
			posY -= g.getHeight();
			break;
		default:
			Logger.warn("Entity attacked in non-uniform direction: " + direction);
			posX += (width - g.getWidth()) / 2;
			posY -= g.getHeight();
		}
		PointSpawn s = new PointSpawn();
		s.setSpawnLocation(new Vector2f(posX, posY));
		HitCollision c = new HitCollision(element, damage);
		c.setTeam(team);
		FlyingMovement m = new FlyingMovement(10);
		particle.addComponent(s);
		particle.addComponent(c);
		particle.addComponent(m);
		particle.addComponent(new RangeControl(this.range));
		s.spawn();

		ShootEmUp.getGame().getCurrentLevel().addEntity(particle);
	}

	public void initSystem() {
		Map<String, JsonArray> jsonObjects = findFiles(directoryPath);
		for (Entry<String, JsonArray> type : jsonObjects.entrySet()) {
			addToSystem(type.getKey(), type.getValue(), Weapon.class);
		}
	}

	public boolean isMelee() {
		return this.melee;
	}

	public int getDamage() {
		return this.damage;
	}

	public Element getElement() {
		return this.element;
	}

	public int getFireRate() {
		return this.fireRate;
	}

	public int getManaCost() {
		return this.manaCost;
	}

	public String getParticleImage() {
		return particleImage;
	}

	public int getRange() {
		return this.range;
	}

	public String getSubType() {
		return this.name;
	}

	public int getTeam() {
		return this.team;
	}

	public String getType() {
		return this.type;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public void setMelee(boolean melee) {
		this.melee = melee;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	@Override
	public Map<String, Map<String, Weapon>> getSystem() {
		return weaponSystem;
	}

	@Override
	protected String getDirectoryPath() {
		return directoryPath;
	}
}

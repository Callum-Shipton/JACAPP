package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;

import components.TypeComponent;
import components.collision.HitCollision;
import components.control.RangeControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.movement.FlyingMovement;
import components.spawn.PointSpawn;
import display.Art;
import display.Image;
import main.Logger;
import main.ShootEmUp;
import math.Vector2;

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
				if (typedWeapons.getValue().containsKey(typeName)){
					w = typedWeapons.getValue().get(typeName);
					this.type = typedWeapons.getKey();
				}
			}
		}

		if(w == null){
			// Pick a random weapon
			Logger.warn("A weapon was created with unknown name/type: " + typeName);
		}
		
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

	public void attack(Entity e, int direction) {
		BaseGraphics BG = e.getComponent(TypeComponent.GRAPHICS);
		float posX = BG.getX();
		float posY = BG.getY();
		float BGWidth = BG.getWidth();
		float BGHeight = BG.getHeight();

		// create particle
		Entity particle = new Entity();
		AnimatedGraphics g = new AnimatedGraphics(getParticleImage(), Art.base, false, 1f);
		g.setDirection(direction);
		particle.addComponent(g);

		switch (direction) {
		case 0:
			posX += (BGWidth - g.getWidth()) / 2;
			posY -= g.getHeight();
			break;
		case 1:
			posX += BGWidth;
			posY -= g.getHeight();
			break;
		case 2:
			posX += BGWidth;
			posY += (BGHeight - g.getHeight()) / 2;
			break;
		case 3:
			posX += BGWidth;
			posY += BGHeight;
			break;
		case 4:
			posX += (BGWidth - g.getWidth()) / 2;
			posY += BGHeight;
			break;
		case 5:
			posX -= g.getWidth();
			posY += BGHeight;
			break;
		case 6:
			posX -= g.getWidth();
			posY += (BGHeight - g.getHeight()) / 2;
			break;
		case 7:
			posX -= g.getWidth();
			posY -= g.getHeight();
			break;
		default:
			Logger.warn("Entity attacked in non-uniform direction: " + direction);
			posX += (BGWidth - g.getWidth()) / 2;
			posY -= g.getHeight();
		}
		PointSpawn s = new PointSpawn(g, new Vector2(posX, posY), particle);
		HitCollision c = new HitCollision(this);
		FlyingMovement m = new FlyingMovement(c, g, 10);
		particle.addComponent(s);
		particle.addComponent(c);
		particle.addComponent(m);
		particle.addComponent(new RangeControl(g, m, this.range));

		ShootEmUp.getCurrentLevel().addEntity(particle);
		// ShootEmUp.getCurrentLevel().getSpawner().checkSpawn(particle);
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

	public Image getParticleImage() {
		return Art.getImage(this.particleImage);
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

package object;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

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

public class Weapon extends InventoryItem {

	private static final long serialVersionUID = 529276641692099199L;

	private static HashMap<String, HashMap<String, Weapon>> weaponSystem;

	private transient String type;
	private int damage;
	private int range;
	private int fireRate;
	private boolean melee;
	private int manaCost;
	private transient int team;
	private String particleImage;
	private Element element;

	public Weapon(String type, int team) {
		if (weaponSystem == null) {
			initSystem();
		}
		Weapon w;
		if (weaponSystem.containsKey(type)) {
			int temp = rand.nextInt(weaponSystem.get(type).size());
			Weapon[] typedWeapons = new Weapon[weaponSystem.get(type).size()];
			typedWeapons = weaponSystem.get(type).values().toArray(typedWeapons);
			w = typedWeapons[temp];
		} else {
			HashMap<String, Weapon> tempWeapons = new HashMap<>();
			for (HashMap<String, Weapon> typedWeapons : weaponSystem.values()) {
				tempWeapons.putAll(typedWeapons);
			}
			w = tempWeapons.get(type);
		}

		this.type = type;
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
		HitCollision c = new HitCollision(particle, this);
		FlyingMovement m = new FlyingMovement(particle, c, g, 10);
		particle.addComponent(s);
		particle.addComponent(c);
		particle.addComponent(m);
		particle.addComponent(new RangeControl(g, m, this.range));

		ShootEmUp.getCurrentLevel().getSpawner().checkSpawn(particle);
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

	@Override
	public void initSystem() {
		weaponSystem = new HashMap<>();
		findFiles("res/Objects/Items/Weapons");
	}

	public boolean isMelee() {
		return this.melee;
	}

	@Override
	public void readJSON(String path, String fileName) {
		JsonReader in = null;
		try (FileInputStream fileInput = new FileInputStream(path)) {
			in = new JsonReader(new InputStreamReader(fileInput));
		
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for (JsonElement e : jsonObjects) {
				String subType = fileName.substring(0, fileName.length() - 5);
				String name = e.getAsJsonObject().get("name").getAsString();
				if (!weaponSystem.containsKey(subType)) {
					weaponSystem.put(subType, new HashMap<String, Weapon>());
				}
				Weapon w = g.fromJson(e, Weapon.class);
				w.type = subType;
				weaponSystem.get(subType).put(name, w);
			}
		} catch (IOException e) {
			Logger.error(e);
		}
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
}

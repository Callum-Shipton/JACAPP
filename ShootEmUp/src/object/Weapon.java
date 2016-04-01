package object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.TypeComponent;
import components.collision.HitCollision;
import components.control.RangeControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.SubTypeWeapon;
import components.inventory.TypePickup;
import components.movement.FlyingMovement;
import components.spawn.PointSpawn;
import display.Art;
import display.Image;
import main.ShootEmUp;
import math.Vector2;

public class Weapon extends InventoryItem {

	private static HashMap<String, ArrayList<Weapon>> weaponSystem;
	private static Random rand = new Random();
	private static Gson g;

	private String type;
	private String subType;
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
			initWeapons();
		}
		
		int temp = rand.nextInt(weaponSystem.get(type).size());
		
		Weapon w = weaponSystem.get(type).get(temp);

		
		this.type = type;
		this.subType = w.subType;
		this.damage = w.damage;
		this.range = w.range;
		this.fireRate = w.fireRate;
		this.melee = w.melee;
		this.manaCost = w.manaCost;
		this.element = w.element;
		this.team = team;
		this.particleImage = w.particleImage;
		this.inventoryImage = w.inventoryImage;
		typePickup = TypePickup.WEAPON;
	}

	private void initWeapons() {
		g = (new GsonBuilder()).setPrettyPrinting().create();
		weaponSystem = new HashMap<String, ArrayList<Weapon>>();
		JsonReader in = null;
		try {
			in = new JsonReader(new InputStreamReader(new FileInputStream("/Items/Weapons.JSON")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (in != null) {
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for(JsonElement e : jsonObjects){
				String type = e.getAsJsonObject().get("type").getAsString();
				if(!weaponSystem.containsKey(type)){
					weaponSystem.put(type, new ArrayList<Weapon>());
				}
				weaponSystem.get(type).add(g.fromJson(e, Weapon.class));
			}
		}
	}

	public void attack(Entity e, int direction) {
		BaseGraphics BG = (BaseGraphics) e.getComponent(TypeComponent.GRAPHICS);
		float posX = BG.getX();
		float posY = BG.getY();
		// create particle
		Entity particle = new Entity();
		AnimatedGraphics g = new AnimatedGraphics(getParticleImage(), Art.base, false);
		g.setDirection(direction);
		particle.addComponent(g);
		switch (direction) {
			case 0:
				posX += (BG.getWidth() - g.getWidth()) / 2;
				posY -= g.getHeight();
				break;
			case 1:
				posX += BG.getWidth();
				posY -= g.getHeight();
				break;
			case 2:
				posX += BG.getWidth();
				posY += (BG.getHeight() - g.getHeight()) / 2;
				break;
			case 3:
				posX += BG.getWidth();
				posY += BG.getHeight();
				break;
			case 4:
				posX += (BG.getWidth() - g.getWidth()) / 2;
				posY += BG.getHeight();
				break;
			case 5:
				posX -= g.getWidth();
				posY += BG.getHeight();
				break;
			case 6:
				posX -= g.getWidth();
				posY += (BG.getHeight() - g.getHeight()) / 2;
				break;
			case 7:
				posX -= g.getWidth();
				posY -= g.getHeight();
		}
		PointSpawn s = new PointSpawn(g, new Vector2(posX, posY), particle);
		HitCollision c = new HitCollision(particle, this);
		FlyingMovement m = new FlyingMovement(particle, c, g, 10);
		particle.addComponent(s);
		particle.addComponent(c);
		particle.addComponent(m);
		particle.addComponent(new RangeControl(g, m, range));

		ShootEmUp.currentLevel.spawner.checkSpawn(particle);
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean isMelee() {
		return melee;
	}

	public void setMelee(boolean melee) {
		this.melee = melee;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}


	public Image getParticleImage() {
		return Art.getImage(particleImage);
	}

	public Element getElement() {
		return element;
	}
	
	public String getSubType(){
		return subType;
	}
	
	public String getType(){
		return type;
	}
}

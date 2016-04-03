package object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

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
import main.ShootEmUp;
import math.Vector2;

public class Weapon extends InventoryItem{

	private static HashMap<String, HashMap<String,Weapon>> weaponSystem;
	private static Random rand = new Random();


	private transient String type;
	private int damage;
	private int range;
	private int fireRate;
	private boolean melee;
	private int manaCost;
	private transient int team;
	private String particleImage;
	private Element element;

	
	public Weapon(String type, int team){
		if (weaponSystem == null) {
			initSystem();
		}
		Weapon w;
		if(weaponSystem.containsKey(type)){
			int temp = rand.nextInt(weaponSystem.get(type).size());
			Weapon[] typedWeapons = new Weapon[weaponSystem.get(type).size()];
			typedWeapons = weaponSystem.get(type).values().toArray(typedWeapons);
			w = typedWeapons[temp];
		}else{
			HashMap<String, Weapon> tempWeapons = new HashMap<String,Weapon>();
			for(HashMap<String,Weapon> typedWeapons : weaponSystem.values()){
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
		typePickup = TypePickup.WEAPON;
	}

	@Override
	public void initSystem() {
		weaponSystem = new HashMap<String, HashMap<String,Weapon>>();
		findFiles("res\\Objects\\Items\\Weapons");
	}
	
	@Override
	public void readJSON(String path, String fileName) {
		JsonReader in = null;
		try {
			in = new JsonReader(new InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (in != null) {
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for(JsonElement e : jsonObjects){
				String type = fileName.substring(0, fileName.length()-5);
				String name = e.getAsJsonObject().get("name").getAsString();
				if(!weaponSystem.containsKey(type)){
					weaponSystem.put(type, new HashMap<String,Weapon>());
				}
				Weapon w = g.fromJson(e, Weapon.class);
				w.type = type;
				weaponSystem.get(type).put(name,w);
			}
		}
	}

	public void attack(Entity e, int direction) {
		BaseGraphics BG = (BaseGraphics) e.getComponent(TypeComponent.GRAPHICS);
		float posX = BG.getX();
		float posY = BG.getY();
		float BGWidth = BG.getWidth();
		float BGHeight = BG.getHeight();
		
		// create particle
		Entity particle = new Entity();
		AnimatedGraphics g = new AnimatedGraphics(getParticleImage(), Art.base, false);
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
		return name;
	}
	
	public String getType(){
		return type;
	}
}

package Level;

import java.util.HashSet;
import java.util.Iterator;

import Components.ComponentType;
import Components.Attack.MageAttack;
import Components.Collision.RigidCollision;
import Components.Collision.BaseCollision;
import Components.Control.PlayerControl;
import Components.Graphical.BaseGraphics;
import Components.Graphical.PlayerGraphics;
import Components.Inventory.PlayerInventory;
import Components.Movement.BasicMovement;
import Components.Spawn.PointSpawn;

import Display.Art;
import GUI.Hud;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;
import Object.EntityMap;
import Object.Weapon;

public class Level {
	
	private Entity player;
	private Hud hud;
	public EntityMap eMap;

	public HashSet<Entity> entities;
	public HashSet<Entity> oldEntities;
	public HashSet<Entity> newEntities;
	
	public Spawner spawner;
	public Map map;
	
	public Level(String file) {
		map = new Map(file);
		eMap = new EntityMap(map.getWidth(), map.getHeight());
		spawner = new Spawner();
		entities = new HashSet<Entity>();
		oldEntities = new HashSet<Entity>();
		newEntities = new HashSet<Entity>();
	}
	
	public void init(){
		map.setTiles();
		createPlayer();
	}

	public void createPlayer(){
		player = new Entity();
		PlayerGraphics g = new PlayerGraphics(player, Art.player, Art.base);
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
		PlayerInventory i = new PlayerInventory(0, 1, 3);
		MageAttack a = new MageAttack(s, i, new Weapon(5, 100, 10, false, 1), 18, 100, 18, 18, 50, 18);
		player.addComponent(g);
		RigidCollision c = new RigidCollision(player);
		player.addComponent(c);
		BasicMovement m = new BasicMovement(player,c, g, 5);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(new PlayerControl(player, g, a, m));
		player.addComponent(i);
		
		//create HUD
		hud = new Hud(player);
		
		entities.add(player);
	}
	
	public void update() {
		spawner.update();
		
		Iterator<Entity> charIter = entities.iterator();
		while(charIter.hasNext()){
			Entity c = charIter.next();
			c.update();
		}
		
		Iterator<Entity> oldEntitiesIter = oldEntities.iterator();
		while(oldEntitiesIter.hasNext()){
			Entity n = oldEntitiesIter.next();
			ShootEmUp.currentLevel.eMap.removeEntity(((BaseCollision)n.getComponent(ComponentType.COLLISION)).getGridPos(), n);
			entities.remove(n);
		}
		oldEntities.clear();
		
		Iterator<Entity> newEntitiesIter = newEntities.iterator();
		while(newEntitiesIter.hasNext()){
			Entity n = newEntitiesIter.next();
			entities.add(n);
		}
		newEntities.clear();
		hud.update();
	}

	public void render() {
		map.renderLowTiles();

		for (Entity character : entities) {
			((BaseGraphics)character.getComponent(ComponentType.GRAPHICS)).render(character);
		}

		map.renderHighTiles();

		hud.render(Art.stat);

	}

	public Entity getPlayer() {
		return player;
	}
	
	/*
	public Entity getWall(Vector2 vec){
		return  walls.get(vec);
	}
	*/
}

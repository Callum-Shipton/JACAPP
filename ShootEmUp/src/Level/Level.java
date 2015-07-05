package Level;

import java.util.HashSet;
import java.util.Iterator;

import Components.ComponentType;
import Components.Collision.BaseCollision;
import Components.Graphical.BaseGraphics;

import Display.Art;
import GUI.Hud;
import Main.ShootEmUp;
import Object.Entity;
import Object.EntityMap;

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
		player = spawner.createPlayer();
		entities.add(player);
		hud = new Hud(player);
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
}

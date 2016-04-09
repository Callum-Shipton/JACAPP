package level;

import java.util.HashSet;
import java.util.Iterator;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import gui.Hud;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;
import object.EntityMap;

public class Level {

	private int level;
	
	public EntityMap eMap;

	private HashSet<Entity> entities;
	private HashSet<Entity> oldEntities;
	private HashSet<Entity> newEntities;
	
	public HashSet<Vector2> walls;
	
	public Spawner spawner;
	public Map map;

	public Level(String file, int level) {
		this.level = level;
		map = new Map(file + level + ".png");
		eMap = new EntityMap(map.getWidth(), map.getHeight());
		spawner = new Spawner();
		entities = new HashSet<Entity>();
		oldEntities = new HashSet<Entity>();
		newEntities = new HashSet<Entity>();
	}

	public void init() {
		addEntity(ShootEmUp.getPlayer());
		ShootEmUp.setHud(new Hud(ShootEmUp.getPlayer(), 0, 0));
		map.init();
	}

	public void update() {
		spawner.update();

		addEntities();
		
		Iterator<Entity> EntityIter = entities.iterator();
		while (EntityIter.hasNext()) {
			Entity c = EntityIter.next();
			c.update();
		}
		
		removeEntities();
	}
	
	public void addEntities(){
		Iterator<Entity> newEntitiesIter = newEntities.iterator();
		while (newEntitiesIter.hasNext()) {
			Entity n = newEntitiesIter.next();
			boolean res = entities.add(n);
			if (!res) {
				System.out.println("New entity not added. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		newEntities.clear();
	}
	
	public void removeEntities(){
		Iterator<Entity> oldEntitiesIter = oldEntities.iterator();
		while (oldEntitiesIter.hasNext()) {
			Entity n = oldEntitiesIter.next();
			BaseCollision BC = ShootEmUp.getPlayer().getComponent(TypeComponent.COLLISION);
			ShootEmUp.getCurrentLevel().eMap
					.removeEntity(BC.getGridPos(), n);
			boolean res = entities.remove(n);
			if (!res) {
				System.out.println("Old entity not removed. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		oldEntities.clear();
	}

	public void render() {
		map.renderLowTiles();

		for (Entity character : entities) {
			BaseGraphics BG = character.getComponent(TypeComponent.GRAPHICS);
			BG.render(character);
		}

		map.renderHighTiles();
	}

	public void addEntity(Entity e){
		newEntities.add(e);
		BaseCollision BC = e.getComponent(TypeComponent.COLLISION);
		if(BC != null){
			BC.setGridPos(eMap.getGridPos(e, e.getComponent(TypeComponent.GRAPHICS)));
			ShootEmUp.getCurrentLevel().eMap.addEntity(eMap.getGridPos(e, e.getComponent(TypeComponent.GRAPHICS)), e);
		}
	}
	
	public void removeEntity(HashSet<Vector2> gridPos, Entity e){
		ShootEmUp.getCurrentLevel().eMap.removeEntity(gridPos, e);
		oldEntities.add(e);
	}
	
	public int getLevel() {
		return level;
	}

	public Spawner getSpawner() {
		return spawner;
	}

	public HashSet<Entity> getEntities() {
		return entities;
	}
}

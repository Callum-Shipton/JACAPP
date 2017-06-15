package level;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import gui.Hud;
import main.Logger;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;
import object.EntityMap;

public class Level {

	private int level;

	private EntityMap eMap;

	private Set<Entity> entities;
	private Set<Entity> oldEntities;
	private Set<Entity> newEntities;

	private Spawner spawner;
	private LevelMap map;

	public Level(String file, int level) {
		this.level = level;
		map = new LevelMap(file + level + ".png");
		eMap = new EntityMap(map.getWidth(), map.getHeight());
		spawner = new Spawner(1, 10, 3, 250.0f, 25.0f);
		entities = new HashSet<>();
		oldEntities = new HashSet<>();
		newEntities = new HashSet<>();
	}

	public void init() {
		addEntity(ShootEmUp.getPlayer());
		ShootEmUp.setHud(new Hud(ShootEmUp.getPlayer(), 0, 0));
		map.init();
	}

	public void addEntities() {
		Iterator<Entity> newEntitiesIter = this.newEntities.iterator();
		while (newEntitiesIter.hasNext()) {
			Entity n = newEntitiesIter.next();
			boolean res = this.entities.add(n);
			if (!res) {
				Logger.warn("New entity not added. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		this.newEntities.clear();
	}

	public void addEntity(Entity e) {
		this.newEntities.add(e);
		BaseCollision BC = e.getComponent(TypeComponent.COLLISION);
		if (BC != null) {
			BC.setGridPos(eMap.getGridPos(e));
			ShootEmUp.getCurrentLevel().eMap.addEntity(eMap.getGridPos(e), e);
		}
	}

	public void removeEntities() {
		Iterator<Entity> oldEntitiesIter = this.oldEntities.iterator();
		while (oldEntitiesIter.hasNext()) {
			Entity n = oldEntitiesIter.next();
			BaseCollision BC = n.getComponent(TypeComponent.COLLISION);
			ShootEmUp.getCurrentLevel().eMap.removeEntity(BC.getGridPos(), n);
			boolean res = this.entities.remove(n);
			if (!res) {
				Logger.warn("Old entity not removed. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		this.oldEntities.clear();
	}

	public void removeEntity(Set<Vector2> gridPos, Entity e) {
		ShootEmUp.getCurrentLevel().eMap.removeEntity(gridPos, e);
		this.oldEntities.add(e);
	}

	public void render() {
		this.map.renderLowTiles();

		for (Entity character : this.entities) {
			BaseGraphics BG = character.getComponent(TypeComponent.GRAPHICS);
			BG.render(character);
		}

		this.map.renderHighTiles();
	}

	public void update() {
		this.spawner.update();

		Iterator<Entity> EntityIter = this.entities.iterator();
		while (EntityIter.hasNext()) {
			Entity c = EntityIter.next();
			c.update();
		}
		
		addEntities();

		removeEntities();
	}

	public EntityMap geteMap() {
		return this.eMap;
	}

	public Set<Entity> getEntities() {
		return this.entities;
	}

	public int getLevel() {
		return this.level;
	}

	public LevelMap getMap() {
		return this.map;
	}

	public Spawner getSpawner() {
		return this.spawner;
	}
}

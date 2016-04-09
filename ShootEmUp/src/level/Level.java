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

	private EntityMap eMap;

	private HashSet<Entity> entities;
	private HashSet<Entity> oldEntities;
	private HashSet<Entity> newEntities;

	private Spawner spawner;
	private Map map;

	public Level(String file, int level) {
		this.level = level;
		this.map = new Map(file + level + ".png");
		this.eMap = new EntityMap(this.map.getWidth(), this.map.getHeight());
		this.spawner = new Spawner();
		this.entities = new HashSet<Entity>();
		this.oldEntities = new HashSet<Entity>();
		this.newEntities = new HashSet<Entity>();
	}

	public void addEntities() {
		Iterator<Entity> newEntitiesIter = this.newEntities.iterator();
		while (newEntitiesIter.hasNext()) {
			Entity n = newEntitiesIter.next();
			boolean res = this.entities.add(n);
			if (!res) {
				System.out.println("New entity not added. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		this.newEntities.clear();
	}

	public void addEntity(Entity e) {
		this.newEntities.add(e);
		BaseCollision BC = e.getComponent(TypeComponent.COLLISION);
		if (BC != null) {
			BC.setGridPos(this.eMap.getGridPos(e, e.getComponent(TypeComponent.GRAPHICS)));
			ShootEmUp.getCurrentLevel().eMap.addEntity(this.eMap.getGridPos(e, e.getComponent(TypeComponent.GRAPHICS)),
					e);
		}
	}

	public EntityMap geteMap() {
		return this.eMap;
	}

	public HashSet<Entity> getEntities() {
		return this.entities;
	}

	public int getLevel() {
		return this.level;
	}

	public Map getMap() {
		return this.map;
	}

	public Spawner getSpawner() {
		return this.spawner;
	}

	public void init() {
		addEntity(ShootEmUp.getPlayer());
		ShootEmUp.setHud(new Hud(ShootEmUp.getPlayer(), 0, 0));
		this.map.init();
	}

	public void removeEntities() {
		Iterator<Entity> oldEntitiesIter = this.oldEntities.iterator();
		while (oldEntitiesIter.hasNext()) {
			Entity n = oldEntitiesIter.next();
			BaseCollision BC = ShootEmUp.getPlayer().getComponent(TypeComponent.COLLISION);
			ShootEmUp.getCurrentLevel().eMap.removeEntity(BC.getGridPos(), n);
			boolean res = this.entities.remove(n);
			if (!res) {
				System.out.println("Old entity not removed. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		this.oldEntities.clear();
	}

	public void removeEntity(HashSet<Vector2> gridPos, Entity e) {
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

		addEntities();

		Iterator<Entity> EntityIter = this.entities.iterator();
		while (EntityIter.hasNext()) {
			Entity c = EntityIter.next();
			c.update();
		}

		removeEntities();
	}
}

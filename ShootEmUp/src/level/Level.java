package level;

import java.util.HashSet;
import java.util.Iterator;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import display.Art;
import gui.Hud;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;
import object.EntityMap;

public class Level {

	private int level;

	private Entity player;
	public Hud hud;

	public EntityMap eMap;

	public HashSet<Entity> entities;
	public HashSet<Entity> oldEntities;
	public HashSet<Entity> newEntities;
	
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
		map.init();
	}

	public void update() {
		spawner.update();

		Iterator<Entity> charIter = entities.iterator();
		while (charIter.hasNext()) {
			Entity c = charIter.next();
			c.update();
		}

		Iterator<Entity> newEntitiesIter = newEntities.iterator();
		while (newEntitiesIter.hasNext()) {
			Entity n = newEntitiesIter.next();
			boolean res = entities.add(n);
			if (!res) {
				System.out.println("New entity not added. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		newEntities.clear();

		Iterator<Entity> oldEntitiesIter = oldEntities.iterator();
		while (oldEntitiesIter.hasNext()) {
			Entity n = oldEntitiesIter.next();
			BaseCollision BC = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.COLLISION);
			ShootEmUp.currentLevel.eMap
					.removeEntity(BC.getGridPos(), n);
			boolean res = entities.remove(n);
			if (!res) {
				System.out.println("Old entity not removed. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		oldEntities.clear();
		hud.update();
	}

	public void render() {
		map.renderLowTiles();

		for (Entity character : entities) {
			BaseGraphics BG = character.getComponent(TypeComponent.GRAPHICS);
			BG.render(character);
		}
		BaseGraphics BG = player.getComponent(TypeComponent.GRAPHICS);
		BG.render(player);

		map.renderHighTiles();

		hud.render(Art.stat);

	}

	public Entity getPlayer() {
		return player;
	}

	public int getLevel() {
		return level;
	}

	public Spawner getSpawner() {
		return spawner;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}
}

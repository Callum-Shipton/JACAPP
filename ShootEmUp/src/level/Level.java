package level;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import display.ImageProcessor;
import entity.Entity;
import logging.Logger;
import math.Vector2;
import object.EntityMap;

public class Level {

	private EntityMap eMap;

	private Set<Entity> entities;
	private Set<Entity> oldEntities;
	private Set<Entity> newEntities;

	private Set<Spawner> spawners;
	private LevelMap map;

	private int currentWave = 1;
	private static final int MAX_WAVE = 10;

	private int enemies = 0;
	private int totalEnemies = 0;

	private boolean waveActive = true;

	private int radiusLocation;
	private int radiusLocationInst;

	private static final float radius = 250.0f;
	private static final float radiusIncreasePerLevel = 25.0f;

	private boolean levelFinished = false;

	public Level(String file, int level) {
		map = new LevelMap(file + level + ".png");
		map.init();
		eMap = new EntityMap(map.getWidth(), map.getHeight());
		spawners = new HashSet<>();

		entities = new HashSet<>();
		oldEntities = new HashSet<>();
		newEntities = new HashSet<>();

		radiusLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "radius");
		radiusLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "radius");
		changeRadius(0);
	}

	public void addSpawner(List<TypeEnemy> prototypes) {
		spawners.add(new AreaSpawner(1, prototypes, map.getWidth(), map.getHeight(), 0, 0));
	}

	public void changeRadius(float f) {
		GL20.glUseProgram(ImageProcessor.ShaderBase);
		GL20.glUniform1f(radiusLocation, f + radius);
		GL20.glUseProgram(0);
		GL20.glUseProgram(ImageProcessor.ShaderInst);
		GL20.glUniform1f(radiusLocationInst, f + radius);
		GL20.glUseProgram(0);
	}

	public void init() {
		for (Entity wall : map.getWalls().values()) {
			addEntity(wall);
		}
	}

	public void addEntities() {
		Iterator<Entity> newEntitiesIter = newEntities.iterator();
		while (newEntitiesIter.hasNext()) {
			Entity n = newEntitiesIter.next();
			boolean res = entities.add(n);
			if (!res) {
				Logger.warn("New entity not added. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		newEntities.clear();
	}

	public void addEntity(Entity e) {
		newEntities.add(e);
		BaseCollision BC = e.getComponent(TypeComponent.COLLISION);
		if (BC != null) {
			BC.setGridPos(eMap.getGridPos(e));
			eMap.addEntity(eMap.getGridPos(e), e);
		} else {
			Logger.warn("No Collision");
		}
	}

	public void removeEntities() {
		Iterator<Entity> oldEntitiesIter = oldEntities.iterator();
		while (oldEntitiesIter.hasNext()) {
			Entity n = oldEntitiesIter.next();
			BaseCollision BC = n.getComponent(TypeComponent.COLLISION);
			eMap.removeEntity(BC.getGridPos(), n);
			boolean res = entities.remove(n);
			if (!res) {
				Logger.warn("Old entity not removed. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		oldEntities.clear();
	}

	public void removeEntity(Set<Vector2> gridPos, Entity e) {
		eMap.removeEntity(gridPos, e);
		oldEntities.add(e);
	}

	public void render() {
		map.renderLowTiles();

		for (Entity character : entities) {
			BaseGraphics BG = character.getComponent(TypeComponent.GRAPHICS);
			BG.render(character);
		}

		map.renderHighTiles();
	}

	public void update() {
		if (totalEnemies < currentWave) {

			for (Spawner spawner : spawners) {
				spawner.update();

				if (!spawner.getSpawnedEntites().isEmpty()) {
					for (Entity entity : spawner.getSpawnedEntites()) {
						addEntity(entity);
						enemies++;
						totalEnemies++;
					}
				}
			}
		} else {
			waveActive = false;
		}

		if (!waveActive && enemies <= 0) {
			nextWave();
		}

		Iterator<Entity> entityIter = entities.iterator();
		while (entityIter.hasNext()) {
			Entity c = entityIter.next();
			c.update();
		}

		addEntities();

		removeEntities();
	}

	private void nextWave() {
		totalEnemies = 0;
		if (currentWave < MAX_WAVE) {
			waveActive = true;
			currentWave++;
			totalEnemies++;
			changeRadius((currentWave - 1) * radiusIncreasePerLevel);
		} else {
			levelFinished = true;
		}

	}

	public void removeEnemy() {
		enemies--;
	}

	public EntityMap geteMap() {
		return eMap;
	}

	public Set<Entity> getEntities() {
		return entities;
	}

	public LevelMap getMap() {
		return map;
	}

	public int getWave() {
		return currentWave;
	}

	public boolean getLevelFinished() {
		return levelFinished;
	}
}

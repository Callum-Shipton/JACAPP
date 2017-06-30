package level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import display.ImageProcessor;
import entity.Entity;
import gui.Hud;
import logging.Logger;
import main.ShootEmUp;
import math.Vector2;
import object.EntityMap;
import save.Save;
import spawners.Spawner;

public class Level {

	private int levelNumber;

	private EntityMap eMap;

	private Set<Entity> entities;
	private Set<Entity> oldEntities;
	private Set<Entity> newEntities;

	private Set<Spawner> spawners;
	private LevelMap map;

	private int currentWave = 1;
	private final int maxWave;

	private int enemies = 0;
	private int totalEnemies = 0;

	private boolean waveActive = true;

	private final int maxLevel;
	private int radiusLocation;
	private int radiusLocationInst;

	private float radius;
	private final float radiusIncreasePerLevel;

	public Level(String file, int level) {
		this.levelNumber = level;
		map = new LevelMap(file + level + ".png");
		eMap = new EntityMap(map.getWidth(), map.getHeight());
		spawners = new HashSet<>();
		spawners.add(new AreaSpawner(1, new ArrayList<Entity>(), map.getWidth(), map.getHeight(), 0, 0));
		entities = new HashSet<>();
		oldEntities = new HashSet<>();
		newEntities = new HashSet<>();

		radiusLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "radius");
		radiusLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "radius");
		radiusIncreasePerLevel = 1;
		maxWave = 1;
		maxLevel = 3;
		changeRadius(0);
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
		addEntity(ShootEmUp.getPlayer());
		if (ShootEmUp.getHud() == null) {
			ShootEmUp.setHud(new Hud(ShootEmUp.getPlayer(), 0, 0, currentWave));
		}
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
		map.renderLowTiles();

		for (Entity character : entities) {
			BaseGraphics BG = character.getComponent(TypeComponent.GRAPHICS);
			BG.render(character);
		}

		map.renderHighTiles();
	}

	public void update() {
		for (Spawner spawner : spawners) {
			spawner.update();

			if (!spawner.getSpawnedEntites().isEmpty()) {
				for (Entity entity : spawner.getSpawnedEntites()) {
					addEntity(entity);
				}
			}
		}

		Iterator<Entity> entityIter = entities.iterator();
		while (entityIter.hasNext()) {
			Entity c = entityIter.next();
			c.update();
		}

		addEntities();

		removeEntities();
	}

	private void nextLevel() {
		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new Save());
		}
		ShootEmUp.getSave().saveCharacter();
		ShootEmUp.getSave().saveToSystem(1);
		BaseAttack BA = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);
		TypeAttack temp = BA.getAttackType();
		ShootEmUp.setCurrentLevel(
				new Level(ImageProcessor.LEVEL_FILE_LOCATION, ShootEmUp.getCurrentLevel().getLevel() + 1));
		ShootEmUp.getCurrentLevel().init();
		try {
			ShootEmUp.getSave().load(1);
		} catch (Exception e) {
			Logger.error(e);
		}
		ShootEmUp.setPlayer(PlayerBuilder.buildPlayer(temp, ShootEmUp.getSave().getCharacter(temp)));
	}

	public void removeEnemy() {
		enemies--;
	}

	private void nextWave() {
		totalEnemies = 0;
		if (currentWave < maxWave) {
			currentWave++;
			changeRadius((currentWave - 1) * radiusIncreasePerLevel);
		} else if (ShootEmUp.getCurrentLevel().getLevel() < maxLevel) {
			nextLevel();
		}
		this.waveActive = true;
	}

	public EntityMap geteMap() {
		return this.eMap;
	}

	public Set<Entity> getEntities() {
		return this.entities;
	}

	public int getLevel() {
		return this.levelNumber;
	}

	public LevelMap getMap() {
		return this.map;
	}

	public int getWave() {
		return currentWave;
	}
}

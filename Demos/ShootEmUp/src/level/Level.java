package level;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import display.ImageProcessor;
import entity.Entity;
import entity.EntityStorage;
import entity.Spawner;
import logging.Logger;
import maze.Direction;
import maze.MazeTile;
import object.EntityMap;

public class Level {

	private final EntityMap eMap;
	private final EntityStorage entityStorage;

	private final Set<Spawner> spawners;
	private final LevelMap map;

	private int enemies = 0;

	private final int radiusLocation;
	private final int radiusLocationInst;

	private static final float RADIUS = 250.0f;
	private static final float RADIUS_INCREASE_PER_LEVEL = 25.0f;

	private Direction levelState = null;

	public Level(MazeTile mazeTile) {
		map = new LevelMap(mazeTile);
		map.init();
		eMap = new EntityMap(map.getWidth(), map.getHeight());
		spawners = new HashSet<>();

		entityStorage = new EntityStorage();

		radiusLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "radius");
		radiusLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "radius");
		changeRadius(0);
	}

	public void addSpawner(List<String> prototypes) {
		spawners.add(new AreaSpawner(1, prototypes, map.getWidth(), map.getHeight(), 0, 0));
	}

	public void changeRadius(float f) {
		GL20.glUseProgram(ImageProcessor.ShaderBase);
		GL20.glUniform1f(radiusLocation, f + RADIUS);
		GL20.glUseProgram(0);
		GL20.glUseProgram(ImageProcessor.ShaderInst);
		GL20.glUniform1f(radiusLocationInst, f + RADIUS);
		GL20.glUseProgram(0);
	}

	public void init() {
		for (Entity wall : map.getWalls().values()) {
			addEntity(wall);
		}
	}

	public void render() {
		map.renderLowTiles();

		for (Entity character : entityStorage.getEntities()) {
			BaseGraphics graphics = character.getComponent(TypeComponent.GRAPHICS);
			graphics.render(character);
		}

		map.renderHighTiles();
	}

	public void update() {
		if (enemies < 5) {
			for (Spawner spawner : spawners) {
				spawner.update();
				if (!spawner.getSpawnedEntites().isEmpty()) {
					for (Entity entity : spawner.getSpawnedEntites()) {
						addEntity(entity);
						enemies++;
					}
				}
			}
		}

		Iterator<Entity> entityIter = entityStorage.getEntities().iterator();
		while (entityIter.hasNext()) {
			Entity c = entityIter.next();
			c.update();
		}
		for (Entity e : entityStorage.getOldEntities()) {
			BaseCollision collision = e.getComponent(TypeComponent.COLLISION);
			eMap.removeEntity(collision.getGridPos(), e);
		}
		entityStorage.update();
	}

	public void addEntity(Entity e) {
		entityStorage.addEntity(e);
		BaseCollision collision = e.getComponent(TypeComponent.COLLISION);
		if (collision != null) {
			collision.setGridPos(eMap.getGridPos(e));
			eMap.addEntity(eMap.getGridPos(e), e);
		} else {
			Logger.warn("No Collision");
		}
	}

	public void removeEntity(Set<Vector2f> gridPos, Entity e) {
		eMap.removeEntity(gridPos, e);
		entityStorage.removeEntity(e);
	}

	public void removeEnemy() {
		enemies--;
	}

	public EntityMap geteMap() {
		return eMap;
	}

	public LevelMap getMap() {
		return map;
	}

	public Direction getLevelState() {
		return levelState;
	}

	public EntityStorage getEntityStorage() {
		return entityStorage;
	}

	public void setLevelState(Direction direction) {
		levelState = direction;
	}
}
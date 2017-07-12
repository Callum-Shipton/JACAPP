package object;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import components.TypeComponent;
import components.collision.RigidCollision;
import components.graphical.BaseGraphics;
import entity.Entity;
import level.LevelMap;
import math.Vector2;

public class EntityMap {

	private HashMap<Integer, HashMap<Integer, HashSet<Entity>>> map;

	private static final int SPLITS = 6;

	public EntityMap(int w, int h) {
		map = new HashMap<>();
		for (int i = 0; i < ((w / SPLITS) + 1); i++) {
			HashMap<Integer, HashSet<Entity>> mapi = new HashMap<>();
			map.put(i, mapi);
			for (int j = 0; j < ((h / SPLITS) + 1); j++) {
				mapi.put(j, new HashSet<Entity>());
			}
		}
	}

	public void addEntity(Set<Vector2> gridPos, Entity e) {
		for (Vector2 gridPosi : gridPos) {
			HashMap<Integer, HashSet<Entity>> x = map.get((int) gridPosi.x());
			HashSet<Entity> ents = x.get((int) gridPosi.y());
			ents.add(e);
		}
	}

	public Set<Entity> getEntites(Set<Vector2> gridPos) {
		Set<Entity> result = new HashSet<>();
		for (Vector2 gridPosi : gridPos) {
			result.addAll(map.get((int) gridPosi.x()).get((int) gridPosi.y()));
		}
		return result;
	}

	public Set<Vector2> getGridPos(Entity e) {
		BaseGraphics graphics = e.getComponent(TypeComponent.GRAPHICS);
		return getGridPos(graphics);
	}

	public Set<Vector2> getGridPos(BaseGraphics graphics) {
		Set<Vector2> gridPos = new HashSet<>();
		for (int i = (int) Math.floor((graphics.getX() / LevelMap.TILE_WIDTH) / 6); i <= Math
				.floor(((graphics.getX() + graphics.getWidth()) / LevelMap.TILE_WIDTH) / 6); i++) {
			for (int j = (int) Math.floor((graphics.getY() / LevelMap.TILE_WIDTH) / 6); j <= Math
					.floor(((graphics.getY() + graphics.getHeight()) / LevelMap.TILE_WIDTH) / 6); j++) {
				gridPos.add(new Vector2(i, j));
			}
		}
		return gridPos;
	}

	public Set<Entity> getRigidEntites(Set<Vector2> gridPos) {
		Set<Entity> result = new HashSet<>();
		for (Vector2 gridPosi : gridPos) {
			for (Entity e : map.get((int) gridPosi.x()).get((int) gridPosi.y())) {
				if (e.getComponent(TypeComponent.COLLISION) instanceof RigidCollision) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public void removeEntity(Set<Vector2> gridPos, Entity e) {
		for (Vector2 gridPosi : gridPos) {
			((map.get((int) gridPosi.x())).get((int) gridPosi.y())).remove(e);
		}
	}
}

package object;

import java.util.HashMap;
import java.util.HashSet;

import components.TypeComponent;
import components.collision.RigidCollision;
import components.graphical.BaseGraphics;
import level.Map;
import math.Vector2;

public class EntityMap {

	private HashMap<Integer, HashMap<Integer, HashSet<Entity>>> map;

	public EntityMap(int w, int h) {
		this.map = new HashMap<Integer, HashMap<Integer, HashSet<Entity>>>();
		for (int i = 0; i < ((w / 6) + 1); i++) {
			HashMap<Integer, HashSet<Entity>> mapi = new HashMap<Integer, HashSet<Entity>>();
			this.map.put(i, mapi);
			for (int j = 0; j < ((h / 6) + 1); j++) {
				mapi.put(j, new HashSet<Entity>());
			}
		}
	}

	public void addEntity(HashSet<Vector2> gridPos, Entity e) {
		for (Vector2 gridPosi : gridPos) {
			HashMap<Integer, HashSet<Entity>> x = this.map.get((int) gridPosi.x());
			HashSet<Entity> ents = x.get((int) gridPosi.y());
			ents.add(e);
		}
	}

	public HashSet<Entity> getEntites(HashSet<Vector2> gridPos) {
		HashSet<Entity> result = new HashSet<Entity>();
		for (Vector2 gridPosi : gridPos) {
			result.addAll(this.map.get((int) gridPosi.x()).get((int) gridPosi.y()));
		}
		return result;
	}

	public HashSet<Vector2> getGridPos(Entity e) {
		HashSet<Vector2> gridPos = new HashSet<Vector2>();
		BaseGraphics BG = e.getComponent(TypeComponent.GRAPHICS);
		for (int i = (int) Math.floor((BG.getX() / Map.getTileWidth()) / 6); i <= Math
				.floor(((BG.getX() + BG.getWidth()) / Map.getTileWidth()) / 6); i++) {
			for (int j = (int) Math.floor((BG.getY() / Map.getTileWidth()) / 6); j <= Math
					.floor(((BG.getY() + BG.getHeight()) / Map.getTileWidth()) / 6); j++) {
				gridPos.add(new Vector2(i, j));
			}
		}
		return gridPos;
	}

	public HashSet<Vector2> getGridPos(Entity e, BaseGraphics BG) {
		HashSet<Vector2> gridPos = new HashSet<Vector2>();
		for (int i = (int) Math.floor((BG.getX() / Map.getTileWidth()) / 6); i <= Math
				.floor(((BG.getX() + BG.getWidth()) / Map.getTileWidth()) / 6); i++) {
			for (int j = (int) Math.floor((BG.getY() / Map.getTileWidth()) / 6); j <= Math
					.floor(((BG.getY() + BG.getHeight()) / Map.getTileWidth()) / 6); j++) {
				gridPos.add(new Vector2(i, j));
			}
		}
		return gridPos;
	}

	public HashSet<Entity> getRigidEntites(HashSet<Vector2> gridPos) {
		HashSet<Entity> result = new HashSet<Entity>();
		for (Vector2 gridPosi : gridPos) {
			for (Entity e : this.map.get((int) gridPosi.x()).get((int) gridPosi.y())) {
				if (e.getComponent(TypeComponent.COLLISION) instanceof RigidCollision) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public void removeEntity(HashSet<Vector2> gridPos, Entity e) {
		for (Vector2 gridPosi : gridPos) {
			((this.map.get((int) gridPosi.x())).get((int) gridPosi.y())).remove(e);
		}
	}
}

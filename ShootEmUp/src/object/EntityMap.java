package object;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import components.TypeComponent;
import components.collision.RigidCollision;
import components.graphical.BaseGraphics;
import level.Map;
import math.Vector2;

public class EntityMap {

	private HashMap<Integer, HashMap<Integer, HashSet<Entity>>> map;

	public EntityMap(int w, int h) {
		this.map = new HashMap<>();
		for (int i = 0; i < ((w / 6) + 1); i++) {
			HashMap<Integer, HashSet<Entity>> mapi = new HashMap<>();
			this.map.put(i, mapi);
			for (int j = 0; j < ((h / 6) + 1); j++) {
				mapi.put(j, new HashSet<Entity>());
			}
		}
	}

	public void addEntity(Set<Vector2> gridPos, Entity e) {
		for (Vector2 gridPosi : gridPos) {
			HashMap<Integer, HashSet<Entity>> x = this.map.get((int) gridPosi.x());
			HashSet<Entity> ents = x.get((int) gridPosi.y());
			ents.add(e);
		}
	}

	public Set<Entity> getEntites(Set<Vector2> gridPos) {
		Set<Entity> result = new HashSet<>();
		for (Vector2 gridPosi : gridPos) {
			result.addAll(this.map.get((int) gridPosi.x()).get((int) gridPosi.y()));
		}
		return result;
	}

	public Set<Vector2> getGridPos(Entity e) {
		BaseGraphics BG = e.getComponent(TypeComponent.GRAPHICS);
		return getGridPos(BG);
	}

	public Set<Vector2> getGridPos(BaseGraphics BG) {
		Set<Vector2> gridPos = new HashSet<>();
		for (int i = (int) Math.floor((BG.getX() / Map.getTileWidth()) / 6); i <= Math
				.floor(((BG.getX() + BG.getWidth()) / Map.getTileWidth()) / 6); i++) {
			for (int j = (int) Math.floor((BG.getY() / Map.getTileWidth()) / 6); j <= Math
					.floor(((BG.getY() + BG.getHeight()) / Map.getTileWidth()) / 6); j++) {
				gridPos.add(new Vector2(i, j));
			}
		}
		return gridPos;
	}

	public Set<Entity> getRigidEntites(Set<Vector2> gridPos) {
		Set<Entity> result = new HashSet<>();
		for (Vector2 gridPosi : gridPos) {
			for (Entity e : this.map.get((int) gridPosi.x()).get((int) gridPosi.y())) {
				if (e.getComponent(TypeComponent.COLLISION) instanceof RigidCollision) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public void removeEntity(Set<Vector2> gridPos, Entity e) {
		for (Vector2 gridPosi : gridPos) {
			((this.map.get((int) gridPosi.x())).get((int) gridPosi.y())).remove(e);
		}
	}
}

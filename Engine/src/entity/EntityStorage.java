package entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import logging.Logger;

public class EntityStorage {
	private Set<Entity> entities;
	private Set<Entity> oldEntities;
	private Set<Entity> newEntities;

	public EntityStorage() {
		entities = new HashSet<>();
		oldEntities = new HashSet<>();
		newEntities = new HashSet<>();
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

	public void removeEntities() {
		Iterator<Entity> oldEntitiesIter = oldEntities.iterator();
		while (oldEntitiesIter.hasNext()) {
			Entity n = oldEntitiesIter.next();
			boolean res = entities.remove(n);
			if (!res) {
				Logger.warn("Old entity not removed. Name: " + n.toString() + ", HC: " + n.hashCode());
			}
		}
		oldEntities.clear();
	}

	public void addEntity(Entity e) {
		newEntities.add(e);
	}

	public void removeEntity(Entity e) {
		oldEntities.add(e);
	}

	public Set<Entity> getEntities() {
		return entities;
	}

	public Set<Entity> getOldEntities() {
		return oldEntities;
	}

	public void update() {
		addEntities();
		removeEntities();
	}
}

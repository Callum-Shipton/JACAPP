package spawners;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import entity.Entity;
import loop.Loop;

public abstract class Spawner {

	protected int spawnCounter = 0;
	protected final int spawnRate;

	protected List<Entity> entities;
	protected Set<Entity> spawnedEntities = new HashSet<>();
	protected Random rand;

	public Spawner(int spawnRate, List<Entity> entities) {
		this.spawnRate = spawnRate;
		this.entities = entities;

		rand = new Random();
	}

	public Set<Entity> getSpawnedEntites() {
		return spawnedEntities;
	}

	protected Entity chooseEntity() {
		Entity newEntity;

		int prob = rand.nextInt(entities.size());
		newEntity = entities.get(prob).clone();

		return newEntity;
	}

	public void update() {
		spawnedEntities.clear();
		spawnCounter++;
		if (spawnCounter == Loop.ticks(spawnRate)) {
			Entity entity = chooseEntity();
			spawnEntity(entity);
			spawnCounter = 0;
		}
	}

	protected abstract void spawnEntity(Entity entity);
}

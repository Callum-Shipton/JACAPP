package entity;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import loop.GameLoop;

public abstract class Spawner {

	protected int spawnCounter = 0;
	protected final int spawnRate;

	protected List<String> enemies;
	protected Set<Entity> spawnedEntities = new HashSet<>();
	protected Random rand;

	public Spawner(int spawnRate, List<String> enemies) {
		this.spawnRate = spawnRate;
		this.enemies = enemies;

		rand = new Random();
	}

	public Set<Entity> getSpawnedEntites() {
		return spawnedEntities;
	}

	protected Entity chooseEntity() {
		Entity newEntity;

		int prob = rand.nextInt(enemies.size());
		newEntity = new Entity("Characters","Enemies", enemies.get(prob));

		return newEntity;
	}

	public void update() {
		spawnedEntities.clear();
		spawnCounter++;
		if (spawnCounter == GameLoop.ticks(spawnRate)) {
			Entity entity = chooseEntity();
			spawnEntity(entity);
			spawnCounter = 0;
		}
	}

	protected abstract void spawnEntity(Entity entity);
}

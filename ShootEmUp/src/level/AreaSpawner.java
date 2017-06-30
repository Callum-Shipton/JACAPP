package level;

import java.util.List;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import display.ImageProcessor;
import entity.Entity;
import logging.Logger;
import loop.Loop;
import main.ShootEmUp;
import math.Vector2;
import spawners.Spawner;

public class AreaSpawner extends Spawner {

	private final int width;
	private final int height;

	private final int x;
	private final int y;

	public AreaSpawner(int spawnRate, List<Entity> entities, int width, int height, int x, int y) {
		super(spawnRate, entities);

		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	@Override
	protected void spawnEntity(Entity entity) {
		BaseGraphics graphicsComponent = entity.getComponent(TypeComponent.GRAPHICS);
		Vector2 position = getPosition();
		graphicsComponent.setX(position.x());
		graphicsComponent.setY(position.y());
		entity.addComponent(graphicsComponent);

		entities.add(entity);
	}

	private Vector2 getPosition() {

		boolean collide;
		Entity test = new Entity();

		BaseGraphics testGraphics = new AnimatedGraphics(ImageProcessor.getImage("Enemy"), ImageProcessor.base, false,
				1f);
		test.addComponent(testGraphics);

		BaseCollision baseCollision = new RigidCollision();
		test.addComponent(baseCollision);

		BaseMovement baseMovement = new BasicMovement(baseCollision, testGraphics, 5);
		test.addComponent(baseMovement);

		BaseGraphics playerGraphics = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.GRAPHICS);
		float px = playerGraphics.getX();
		float py = playerGraphics.getY();
		float pw = playerGraphics.getWidth();
		float ph = playerGraphics.getHeight();

		Level level = ShootEmUp.getGame().getCurrentLevel();

		int spawnX;
		int spawnY;

		do {
			collide = false;

			spawnX = (rand.nextInt(width - 1) + x) * LevelMap.TILE_WIDTH;
			spawnY = (rand.nextInt(height - 1) + y) * LevelMap.TILE_WIDTH;

			// TODO: Fix garbage patch to enemy spawning
			testGraphics.setX(spawnX + 10);
			testGraphics.setY(spawnY + 10);

			// Checks if the enemy will spawn on screen
			if ((Math.abs((spawnX + (testGraphics.getWidth() / 2)) - (px + (pw / 2))) <= (Loop.getDisplay().getWidth()
					+ testGraphics.getWidth()))
					&& (Math.abs((spawnY + (testGraphics.getHeight() / 2))
							- (py + (ph / 2))) <= (Loop.getDisplay().getHeight() + testGraphics.getHeight()))) {
				continue;
			}

			// Checks if the enemy will spawn on top of an entity
			for (Entity character : level.getEntities()) {
				if (baseMovement.doesCollide(test, character) != null) {
					collide = true;
					break;
				}
			}
		} while (collide);

		Logger.debug("Spawn Location: " + spawnX / LevelMap.TILE_WIDTH + ", " + spawnY / LevelMap.TILE_WIDTH,
				Logger.Category.ENTITIES);
		return new Vector2(spawnX, spawnY);
	}
}

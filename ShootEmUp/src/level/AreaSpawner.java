package level;

import java.util.List;
import java.util.Set;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import display.Image;
import display.ImageProcessor;
import entity.Entity;
import logging.Logger;
import logging.Logger.Category;
import loop.Loop;
import main.ShootEmUp;
import math.Vector2;

public class AreaSpawner extends Spawner {

	private final int width;
	private final int height;

	private final int x;
	private final int y;

	public AreaSpawner(int spawnRate, List<TypeEnemy> enemies, int width, int height, int x, int y) {
		super(spawnRate, enemies);

		Logger.debug("Spawner Size: " + width + ", " + height, Category.ENTITIES);

		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	@Override
	protected void spawnEntity(Entity entity) {
		BaseGraphics graphicsComponent = entity.getComponent(TypeComponent.GRAPHICS);
		Vector2 position = getPosition(graphicsComponent.getImage());
		graphicsComponent.setX(position.x());
		graphicsComponent.setY(position.y());

		Logger.debug("Entity: " + entity.getId() + " at Spawn Location: " + position.x() / LevelMap.TILE_WIDTH + ", "
				+ position.y() / LevelMap.TILE_WIDTH + " Size: "
				+ (graphicsComponent.getHeight() / LevelMap.TILE_WIDTH), Logger.Category.ENTITIES);

		spawnedEntities.add(entity);
	}

	private Vector2 getPosition(Image image) {

		boolean collide;
		Entity test = new Entity();

		BaseGraphics testGraphics = new AnimatedGraphics(image, ImageProcessor.base, false, 1f);
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

		Set<Entity> levelEntities = ShootEmUp.getGame().getCurrentLevel().getEntities();

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
			for (Entity entity : levelEntities) {
				if (baseMovement.doesCollide(test, entity) != null) {
					collide = true;
					break;
				}
			}
		} while (collide);

		return new Vector2(spawnX, spawnY);
	}
}

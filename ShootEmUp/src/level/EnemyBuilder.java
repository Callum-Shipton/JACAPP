package level;

import java.util.Random;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.EnemyAttack;
import components.attack.TypeAttack;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.control.AIControl;
import components.control.BaseControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.BaseInventory;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import display.ImageProcessor;
import entity.Entity;
import logging.Logger;
import loop.Loop;
import main.ShootEmUp;
import object.Armour;
import object.Weapon;

public final class EnemyBuilder {

	private static Entity newEnemy;

	private static BaseGraphics enemyGraphics;
	private static BaseAttack enemyAttack;
	private static BaseCollision enemyCollision;
	private static BaseMovement enemyMovement;

	private static BaseGraphics testGraphics;

	private static Random rand = new Random();

	public static Entity buildEnemy(TypeEnemy type) {

		testEnemy();

		newEnemy = new Entity();

		switch (type) {
		case SMALL:
			Logger.debug("Small Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("SmallEnemy", 7);
			enemyAttack = new EnemyAttack(TypeAttack.WARRIOR, 1, 5, new Weapon("OneHanded", 1), new Armour("Helmet"),
					null, null, null);
			break;
		case NORMAL:
			Logger.debug("Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("Enemy", 4);
			enemyAttack = new EnemyAttack(TypeAttack.ARCHER, 3, 5, new Weapon("Bow", 1), null, new Armour("Chest"),
					null, null);
			break;
		case FLYING:
			Logger.debug("Flying Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("FlyingEnemy", 5);
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 2, 5, new Weapon("Staff", 1), null, null, new Armour("Legs"),
					null);
			break;
		case BOSS:
			Logger.debug("Boss Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("BossEnemy", 5);
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 100, 5, new Weapon("TwoHanded", 1), null, null, null,
					new Armour("Boots"));
			break;
		}

		BaseControl enemyControl = new AIControl(enemyGraphics, enemyAttack, enemyMovement);
		BaseInventory enemyInventory = new BaseInventory(enemyGraphics, enemyAttack, 1);

		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		newEnemy.addComponent(enemyInventory);

		return newEnemy;
	}

	private static void addComponents(String art, int speed) {
		enemyGraphics = new AnimatedGraphics(ImageProcessor.getImage(art), ImageProcessor.base, false,
				testGraphics.getX(), testGraphics.getX());
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision();
		enemyMovement = new BasicMovement(enemyCollision, enemyGraphics, speed);
	}

	private static void testEnemy() {

		boolean collide;
		Entity test = new Entity();

		testGraphics = new AnimatedGraphics(ImageProcessor.getImage("Enemy"), ImageProcessor.base, false, 1f);
		test.addComponent(testGraphics);

		BaseCollision baseCollision = new RigidCollision();
		test.addComponent(baseCollision);

		BaseMovement baseMovement = new BasicMovement(baseCollision, testGraphics, 5);
		test.addComponent(baseMovement);

		BaseGraphics playerGraphics = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		float px = playerGraphics.getX();
		float py = playerGraphics.getY();
		float pw = playerGraphics.getWidth();
		float ph = playerGraphics.getHeight();

		Level level = ShootEmUp.getCurrentLevel();

		int spawnX;
		int spawnY;

		do {
			collide = false;

			spawnX = rand.nextInt(level.getMap().getWidth() - 1) * LevelMap.getTileWidth();
			spawnY = rand.nextInt(level.getMap().getHeight() - 1) * LevelMap.getTileWidth();

			testGraphics.setX(spawnX);
			testGraphics.setY(spawnY);

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

		Logger.debug("Spawn Location: " + spawnX / LevelMap.getTileWidth() + ", " + spawnY / LevelMap.getTileWidth(),
				Logger.Category.ENTITIES);
	}
}

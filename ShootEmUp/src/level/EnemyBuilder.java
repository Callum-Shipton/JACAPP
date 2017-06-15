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
import display.Art;
import main.Loop;
import object.Armour;
import object.Entity;
import object.Weapon;

public final class EnemyBuilder {

	private static Entity newEnemy;

	private static BaseGraphics enemyGraphics;
	private static BaseAttack enemyAttack;
	private static BaseCollision enemyCollision;
	private static BaseMovement enemyMovement;

	private static BaseGraphics testGraphics;

	private static Random rand = new Random();

	public static void buildEnemy(TypeEnemy type) {

		testEnemy();

		newEnemy = new Entity();

		switch (type) {
		case SMALL:
			addComponents("SmallEnemy", 7);
			enemyAttack = new EnemyAttack(TypeAttack.WARRIOR, 1, 5, new Weapon("OneHanded", 1), new Armour("Helmet"),
					null, null, null);
			break;
		case NORMAL:
			addComponents("Enemy", 4);
			enemyAttack = new EnemyAttack(TypeAttack.ARCHER, 3, 5, new Weapon("Bow", 1), null, new Armour("Chest"),
					null, null);
			break;
		case FLYING:
			addComponents("FlyingEnemy", 5);
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 2, 5, new Weapon("Staff", 1), null, null, new Armour("Legs"),
					null);
			break;
		case BOSS:
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

		Loop.getCurrentLevel().addEntity(newEnemy);
	}

	private static void addComponents(String art, int speed) {
		enemyGraphics = new AnimatedGraphics(Art.getImage(art), Art.base, false, testGraphics.getX(),
				testGraphics.getX());
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision();
		enemyMovement = new BasicMovement(enemyCollision, enemyGraphics, speed);
	}

	private static void testEnemy() {

		boolean collide;
		Entity test = new Entity();
		testGraphics = new AnimatedGraphics(Art.getImage("Enemy"), Art.base, false, 1f);
		test.addComponent(testGraphics);
		BaseCollision baseCollision = new RigidCollision();
		test.addComponent(baseCollision);
		test.addComponent(new BasicMovement(baseCollision, testGraphics, 5));
		BaseGraphics playerGraphics = Loop.getPlayer().getComponent(TypeComponent.GRAPHICS);
		float px = playerGraphics.getX();
		float py = playerGraphics.getY();
		float pw = playerGraphics.getWidth();
		float ph = playerGraphics.getHeight();

		Level level = Loop.getCurrentLevel();

		do {
			collide = false;

			testGraphics.setX(rand.nextInt((level.getMap().getBackgroundTiles().length - 1) * LevelMap.getTileWidth()));
			testGraphics.setY(rand.nextInt((level.getMap().getBackgroundTiles()[0].length - 1) * LevelMap.getTileWidth()));

			if ((Math.abs((testGraphics.getX() + (testGraphics.getWidth() / 2))
					- (px + (pw / 2))) <= (Loop.getDisplay().getWidth() + testGraphics.getWidth()))
					&& (Math.abs((testGraphics.getY() + (testGraphics.getHeight() / 2))
							- (py + (ph / 2))) <= (Loop.getDisplay().getHeight() + testGraphics.getHeight()))) {
				continue;
			}

			// changed to grid position

			for (Entity character : level.getEntities()) {
				BaseMovement baseMovement = test.getComponent(TypeComponent.MOVEMENT);
				if (baseMovement.doesCollide(test, character) != null) {
					collide = true;
					break;
				}
			}
		} while (collide);
	}
}

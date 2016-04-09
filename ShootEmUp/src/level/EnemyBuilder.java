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
import components.movement.FlyingMovement;
import display.Art;
import main.ShootEmUp;
import object.Armour;
import object.Entity;
import object.Weapon;

public abstract class EnemyBuilder {

	private static Entity newEnemy;
	private static Entity test;

	private static BaseGraphics enemyGraphics;
	private static BaseAttack enemyAttack;
	private static BaseControl enemyControl;
	private static BaseCollision enemyCollision;
	private static BaseMovement enemyMovement;
	private static BaseInventory enemyInventory;

	private static BaseGraphics TG;

	private static Random rand = new Random();

	public static void buildEnemy(TypeEnemy type) {

		testEnemy();

		newEnemy = new Entity();

		switch (type) {
		case SMALL:
			enemyGraphics = new AnimatedGraphics(Art.getImage("SmallEnemy"), Art.base, false, TG.getX(), TG.getX());
			enemyAttack = new EnemyAttack(TypeAttack.WARRIOR, 1, 5, new Weapon("OneHanded", 1), new Armour("Helmet"),
					null, null, null);
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy, enemyGraphics);
			enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 7);
			break;
		case NORMAL:
			enemyGraphics = new AnimatedGraphics(Art.getImage("Enemy"), Art.base, false, TG.getX(), TG.getX());
			enemyAttack = new EnemyAttack(TypeAttack.ARCHER, 3, 5, new Weapon("Bow", 1), null, new Armour("Chest"),
					null, null);
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy, enemyGraphics);
			enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 4);
			break;
		case FLYING:
			enemyGraphics = new AnimatedGraphics(Art.getImage("FlyingEnemy"), Art.base, false, TG.getX(), TG.getX());
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 2, 5, new Weapon("Staff", 1), null, null, new Armour("Legs"),
					null);
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy, enemyGraphics);
			enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);

			break;
		case BOSS:
			enemyGraphics = new AnimatedGraphics(Art.getImage("BossEnemy"), Art.base, false, TG.getX(), TG.getX());
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 100, 5, new Weapon("TwoHanded", 1), null, null, null,
					new Armour("Boots"));
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy, enemyGraphics);
			enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
			break;
		}

		enemyControl = new AIControl(enemyGraphics, enemyAttack, enemyMovement);
		enemyInventory = new BaseInventory(enemyGraphics, enemyAttack, 1);

		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		newEnemy.addComponent(enemyInventory);

		ShootEmUp.getCurrentLevel().addEntity(newEnemy);
	}

	private static void testEnemy() {

		boolean collide = false;
		test = new Entity();
		TG = new AnimatedGraphics(Art.getImage("Enemy"), Art.base, false, 1f);
		test.addComponent(TG);
		BaseCollision BC = new RigidCollision(test, TG);
		test.addComponent(BC);
		test.addComponent(new BasicMovement(test, BC, TG, 5));
		BaseGraphics playerGraphics = ShootEmUp.getPlayer()
				.getComponent(TypeComponent.GRAPHICS);
		float px = playerGraphics.getX();
		float py = playerGraphics.getY();
		float pw = playerGraphics.getWidth();
		float ph = playerGraphics.getHeight();
		do {
			collide = false;

			TG.setX(rand.nextInt((ShootEmUp.getCurrentLevel().map.getBackgroundTiles().length - 1) * Map.TILE_WIDTH));
			TG.setY(rand.nextInt((ShootEmUp.getCurrentLevel().map.getBackgroundTiles()[0].length - 1) * Map.TILE_WIDTH));

			if ((Math.abs((TG.getX() + (TG.getWidth() / 2)) - (px + (pw / 2))) <= (ShootEmUp.getDisplay().getWidth() + TG.getWidth()))
					&& (Math.abs((TG.getY() + (TG.getHeight() / 2)) - (py + (ph / 2))) <= (ShootEmUp.getDisplay().getHeight()
							+ TG.getHeight()))) {
				collide = true;
				continue;
			}

			// changed to grid position;

			for (Entity character : ShootEmUp.getCurrentLevel().entities) {
				BaseMovement BM = test.getComponent(TypeComponent.MOVEMENT);
				if ((BM.doesCollide(test, character) != null)) {
					collide = true;
					break;
				}
			}
		} while (collide == true);
	}
}

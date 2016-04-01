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
import components.inventory.TypeArmour;
import components.inventory.TypeWeapon;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import components.movement.FlyingMovement;
import display.Art;
import main.ShootEmUp;
import object.ArmourBuilder;
import object.Entity;
import object.WeaponBuilder;

public abstract class EnemyBuilder {

	private static Entity newEnemy;
	private static Entity test;

	private static BaseGraphics enemyGraphics;
	private static BaseAttack enemyAttack;
	private static BaseControl enemyControl;
	private static BaseCollision enemyCollision;
	private static BaseMovement enemyMovement;
	private static BaseInventory enemyInventory;

	private static Random rand = new Random();

	public static void buildEnemy(TypeEnemy type) {

		testEnemy();

		newEnemy = new Entity();

		switch (type) {
			case SMALL:
				enemyGraphics = new AnimatedGraphics(Art.getImage("SmallEnemy"), Art.base, false,
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(),
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX());
				enemyAttack = new EnemyAttack(TypeAttack.WARRIOR, 1, 5,
						WeaponBuilder.buildWeapon(TypeWeapon.ONE_HANDED, 1),
						ArmourBuilder.buildArmour(TypeArmour.HELMET), null, null, null);
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
				enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 7);
				break;
			case NORMAL:
				enemyGraphics = new AnimatedGraphics(Art.getImage("Enemy"), Art.base, false,
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(),
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX());
				enemyAttack = new EnemyAttack(TypeAttack.ARCHER, 3, 5, WeaponBuilder.buildWeapon(TypeWeapon.BOW, 1), null,
						ArmourBuilder.buildArmour(TypeArmour.CHESTPLATE), null, null);
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
				enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 4);
				break;
			case FLYING:
				enemyGraphics = new AnimatedGraphics(Art.getImage("FlyingEnemy"), Art.base, false,
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(),
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX());
				enemyAttack = new EnemyAttack(TypeAttack.MAGE, 2, 5, WeaponBuilder.buildWeapon(TypeWeapon.STAFF, 1), null,
						null, ArmourBuilder.buildArmour(TypeArmour.LEGS), null);
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
				enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);

				break;
			case BOSS:
				enemyGraphics = new AnimatedGraphics(Art.getImage("BossEnemy"), Art.base, false,
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(),
						((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX());
				enemyAttack = new EnemyAttack(TypeAttack.MAGE, 100, 5, WeaponBuilder.buildWeapon(TypeWeapon.TWO_HANDED, 1),
						null, null, null, ArmourBuilder.buildArmour(TypeArmour.BOOTS));
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
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

		ShootEmUp.currentLevel.entities.add(newEnemy);
	}

	private static void testEnemy() {

		boolean collide = false;
		test = new Entity();
		BaseGraphics BG = new AnimatedGraphics(Art.getImage("Enemy"), Art.base, false);
		test.addComponent(BG);
		BaseCollision BC = new RigidCollision(test);
		test.addComponent(BC);
		test.addComponent(new BasicMovement(test, BC, BG, 5));
		BaseGraphics playerGraphics = (BaseGraphics) ShootEmUp.currentLevel.getPlayer()
				.getComponent(TypeComponent.GRAPHICS);
		float px = playerGraphics.getX();
		float py = playerGraphics.getY();
		float pw = playerGraphics.getWidth();
		float ph = playerGraphics.getHeight();
		do {
			collide = false;

			BG.setX(rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles().length - 1) * Map.TILE_WIDTH));
			BG.setY(rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles()[0].length - 1) * Map.TILE_WIDTH));

			if ((Math.abs((BG.getX() + (BG.getWidth() / 2)) - (px + (pw / 2))) <= (ShootEmUp.width + BG.getWidth()))
					&& (Math.abs((BG.getY() + (BG.getHeight() / 2)) - (py + (ph / 2))) <= (ShootEmUp.height
							+ BG.getHeight()))) {
				collide = true;
				continue;
			}

			// changed to grid position;

			for (Entity character : ShootEmUp.currentLevel.entities) {
				if ((((BaseMovement) test.getComponent(TypeComponent.MOVEMENT)).doesCollide(test, character) != null)) {
					collide = true;
					break;
				}
			}
		} while (collide == true);
	}
}

package level;

import java.util.EnumMap;
import java.util.Map;

import components.MessageId;
import components.attack.BaseAttack;
import components.attack.EnemyAttack;
import components.attack.TypeAttack;
import components.audio.BaseAudio;
import components.audio.EventAudio;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.control.AIControl;
import components.control.BaseControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.BaseInventory;
import components.movement.BaseMovement;
import components.movement.GroundMovement;
import display.ImageProcessor;
import entity.Entity;
import logging.Logger;
import object.Armour;
import object.Weapon;

public final class EnemyBuilder {

	private static Entity newEnemy;

	private static BaseGraphics enemyGraphics;
	private static BaseAttack enemyAttack;
	private static BaseCollision enemyCollision;
	private static BaseMovement enemyMovement;

	public static Entity buildEnemy(TypeEnemy type) {

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
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 1, 5, new Weapon("TwoHanded", 1), null, null, null,
					new Armour("Boots"));
			break;
		}

		BaseControl enemyControl = new AIControl();
		BaseInventory enemyInventory = new BaseInventory(1);

		Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);
		sounds.put(MessageId.SHOOT, "Shoot.ogg");
		BaseAudio enemyAudio = new EventAudio(sounds, newEnemy);

		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		newEnemy.addComponent(enemyInventory);
		newEnemy.addComponent(enemyAudio);

		return newEnemy;
	}

	private static void addComponents(String art, int speed) {
		enemyGraphics = new AnimatedGraphics(ImageProcessor.getImage(art), ImageProcessor.base, false, 0, 0);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision();
		enemyMovement = new GroundMovement(speed);
	}
}

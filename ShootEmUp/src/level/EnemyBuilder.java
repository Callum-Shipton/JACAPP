package level;

import java.util.EnumMap;
import java.util.Map;

import components.MessageId;
import components.attack.BaseAttack;
import components.attack.EnemyAttack;
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
import entity.Entity;
import logging.Logger;
import object.Armour;

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
			enemyAttack = new EnemyAttack(1, 5, "OneHanded",0, new Armour("Helmet"), null, null, null, null);
			break;
		case NORMAL:
			Logger.debug("Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("Enemy", 4);
			enemyAttack = new EnemyAttack(3, 5, "Bow",0, null, new Armour("Chest"), null, null, null);
			break;
		case FLYING:
			Logger.debug("Flying Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("FlyingEnemy", 5);
			enemyAttack = new EnemyAttack(2, 5, "Staff",0, null, null, new Armour("Legs"), null, null);
			break;
		case BOSS:
			Logger.debug("Boss Enemy Spawn", Logger.Category.ENTITIES);
			addComponents("BossEnemy", 5);
			enemyAttack = new EnemyAttack(1, 5, "TwoHanded",0, null, null, null, new Armour("Boots"), null);
			break;
		}

		BaseControl enemyControl = new AIControl();
		BaseInventory enemyInventory = new BaseInventory(1);

		Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);
		sounds.put(MessageId.SHOOT, "Shoot.ogg");
		BaseAudio enemyAudio = new EventAudio(sounds);

		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		newEnemy.addComponent(enemyInventory);
		newEnemy.addComponent(enemyAudio);

		return newEnemy;
	}

	private static void addComponents(String art, int speed) {
		enemyGraphics = new AnimatedGraphics(art, false, 0, 0);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision();
		enemyMovement = new GroundMovement(speed);
	}
}

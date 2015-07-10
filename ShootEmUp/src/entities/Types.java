package entities;

import components.attack.EnemyAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.AIControl;
import components.graphical.AnimatedGraphics;
import components.inventory.EnemyInventory;
import components.movement.BasicMovement;
import display.Art;

public class Types {

	public static final EnemyType smallEnemy = new EnemyType(new AnimatedGraphics(Art.smallEnemy, Art.base, false, x, y), new EnemyAttack(TypeAttack.WARRIOR, 1), new AIControl(null, null, null), new BasicMovement(null, null, 7), new RigidCollision(), new EnemyInventory(null, 1));
	public static final EnemyType enemy = new EnemyType(Art.enemy, TypeAttack.ARCHER, 3, 2); 
	public static final EnemyType flyingEnemy = new EnemyType(Art.flyingEnemy, TypeAttack.MAGE, 2, 5); 
	public static final EnemyType bossEnemy = new EnemyType(Art.bossEnemy, TypeAttack.MAGE, 1000, 5); 
}

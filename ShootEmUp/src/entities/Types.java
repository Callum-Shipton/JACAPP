package entities;

import components.attack.TypeAttack;
import display.Art;

public class Types {

	public static final EnemyType smallEnemy = new EnemyType(Art.smallEnemy, TypeAttack.WARRIOR, 1, 7);
	public static final EnemyType enemy = new EnemyType(Art.enemy, TypeAttack.ARCHER, 3, 2); 
	public static final EnemyType flyingEnemy = new EnemyType(Art.flyingEnemy, TypeAttack.MAGE, 2, 5); 
	public static final EnemyType bossEnemy = new EnemyType(Art.bossEnemy, TypeAttack.MAGE, 1000, 5); 
}

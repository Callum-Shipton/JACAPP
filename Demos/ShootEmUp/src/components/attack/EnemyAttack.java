package components.attack;

import components.interfaces.AttackComponent;
import display.Art;
import display.ImageProcessor;
import entity.Entity;
import gui.HudBar;
import logging.Logger;
import logging.Logger.Category;
import main.ShootEmUp;

public class EnemyAttack extends BaseAttack implements AttackComponent {

	private final HudBar healthBar;

	public EnemyAttack(int health, int mana, String weaponId, int team, String helmetId, String chestId, String legsId,
			String bootsId) {
		super(health, mana, weaponId, team, helmetId, chestId, legsId, bootsId);

		healthBar = new HudBar(10.0f, 10.0f, Art.getImage("Bars"), 1, 0.25f);
	}

	public EnemyAttack(EnemyAttack enemyAttack) {
		this(enemyAttack.maxHealth, enemyAttack.maxMana, enemyAttack.weaponId, enemyAttack.team, enemyAttack.helmetId,
				enemyAttack.chestId, enemyAttack.legsId, enemyAttack.bootsId);

	}

	@Override
	public void die(Entity e) {
		Logger.debug("Enemy Died: " + e.getId(), Category.ENTITIES);
		e.destroy();
		ShootEmUp.getGame().getCurrentLevel().removeEnemy();
	}

	@Override
	public void update() {
		super.update();
		healthBar.update();
		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
	}
}

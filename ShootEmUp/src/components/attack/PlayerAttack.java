package components.attack;

import components.Message;
import display.Art;
import gui.menus.GameOverMenu;
import main.ShootEmUp;
import object.ArmourBuilder;
import object.Entity;
import object.Weapon;
import object.WeaponBuilder;
import save.CharacterSave;

public class PlayerAttack extends BaseAttack {

	protected int lives;

	public PlayerAttack(TypeAttack type, int health, int mana, Weapon weapon) {
		super(type,health, mana, weapon);
		
		lives = 3;
	}

	public PlayerAttack(TypeAttack type, CharacterSave save) {
		super(type);
		lives = save.getLives();

		health = save.getHealth();
		mana = save.getMana();
		weapon = WeaponBuilder.buildWeapon(save.getWeapon(), 0);
		if (save.getBoots() != null) {
			boots = ArmourBuilder.buildArmour(save.getBoots());
			setArmourValue();
		}
		if (save.getLegs() != null) {
			legs = ArmourBuilder.buildArmour(save.getLegs());
			setArmourValue();
		}
		if (save.getChest() != null) {
			chest = ArmourBuilder.buildArmour(save.getChest());
			setArmourValue();
		}
		if (save.getHelmet() != null) {
			helmet = ArmourBuilder.buildArmour(save.getHelmet());
			setArmourValue();
		}

		maxHealth = save.getMaxHealth();
		maxHealthRegen = save.getMaxHealthRegen();
		maxMana = save.getMaxMana();
		maxManaRegen = save.getMaxManaRegen();
	}

	@Override
	public void damage(int damage, Entity e) {
		super.damage(damage, e);
		if (health <= 0) {
			health = maxHealth;
			mana = maxMana;
			removeLife();
			e.send(Message.ENTITY_DIED);
		}
	}

	public void removeLife() {
		if (lives == -99) {
			ShootEmUp.paused = true;
			ShootEmUp.menuStack.add(new GameOverMenu(Art.getImage("GameOverScreen")));
		} else {
			lives--;
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
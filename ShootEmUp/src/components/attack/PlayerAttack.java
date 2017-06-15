package components.attack;

import components.Message;
import display.Art;
import gui.menus.GameOverMenu;
import main.Loop;
import object.Entity;
import object.Weapon;
import save.CharacterSave;

public class PlayerAttack extends BaseAttack {

	protected int lives;

	public PlayerAttack(TypeAttack type, CharacterSave save) {
		super(type);
		this.lives = save.getLives();

		this.health = save.getHealth();
		this.mana = save.getMana();
		this.weapon = save.getWeapon();
		this.boots = save.getBoots();
		this.legs = save.getLegs();
		this.chest = save.getChest();
		this.helmet = save.getHelmet();
		setArmourValue();

		this.maxHealth = save.getMaxHealth();
		this.maxHealthRegen = save.getMaxHealthRegen();
		this.maxMana = save.getMaxMana();
		this.maxManaRegen = save.getMaxManaRegen();
	}

	public PlayerAttack(TypeAttack type, int health, int mana, Weapon weapon) {
		super(type, health, mana, weapon);
		this.lives = 3;
	}

	@Override
	public void die(Entity e) {
		this.health = this.maxHealth;
		this.mana = this.maxMana;
		removeLife();
		e.send(Message.ENTITY_DIED);
	}

	public int getLives() {
		return this.lives;
	}

	public void removeLife() {
		if (this.lives == 1) {
			Loop.setPaused(true);
			Loop.getMenuSystem().addMenu(new GameOverMenu(Art.getImage("GameOverScreen")));
		} else {
			this.lives--;
		}
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
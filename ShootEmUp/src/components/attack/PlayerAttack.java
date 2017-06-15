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
		
		lives = save.getLives();

		health = save.getHealth();
		mana = save.getMana();
		weapon = save.getWeapon();
		boots = save.getBoots();
		legs = save.getLegs();
		chest = save.getChest();
		helmet = save.getHelmet();
		setArmourValue();

		maxHealth = save.getMaxHealth();
		maxHealthRegen = save.getMaxHealthRegen();
		maxMana = save.getMaxMana();
		maxManaRegen = save.getMaxManaRegen();
	}

	public PlayerAttack(TypeAttack type, int health, int mana, Weapon weapon) {
		super(type, health, mana, weapon);
		lives = 3;
	}

	@Override
	public void die(Entity e) {
		removeLife();
		e.send(Message.ENTITY_DIED);
	}

	public void removeLife() {
		if (lives == 1) {
			Loop.setPaused(true);
			Loop.getMenuSystem().addMenu(new GameOverMenu(Art.getImage("GameOverScreen")));
		} else {
			lives--;
			health = maxHealth;
			mana = maxMana;
		}
	}
	
	public int getLives() {
		return lives;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}
}
package components.attack;

import components.Message;
import components.MessageId;
import display.ImageProcessor;
import entity.Entity;
import gui.menus.GameOverMenu;
import main.ShootEmUp;
import object.Weapon;
import save.CharacterSave;

public class PlayerAttack extends BaseAttack {

	protected int lives;

	public PlayerAttack(TypeAttack type, CharacterSave save, Entity entity) {
		super(type, entity);

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

	public PlayerAttack(TypeAttack type, int health, int mana, Weapon weapon, Entity entity) {
		super(type, health, mana, weapon, entity);
		lives = 3;
	}

	@Override
	public void die(Entity e) {
		removeLife();
		e.send(new Message(MessageId.ENTITY_DIED));
	}

	public void removeLife() {
		if (lives == 1) {
			ShootEmUp.setPaused(true);
			ShootEmUp.getMenuSystem().addMenu(new GameOverMenu(ImageProcessor.getImage("GameOverScreen")));
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
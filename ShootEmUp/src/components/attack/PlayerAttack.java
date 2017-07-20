package components.attack;

import java.util.HashSet;
import java.util.Set;

import components.Message;
import components.MessageId;
import display.ImageProcessor;
import entity.Entity;
import gui.menus.GameOverMenu;
import main.ShootEmUp;
import save.CharacterSave;

public class PlayerAttack extends BaseAttack {

	protected int lives;

	public PlayerAttack(CharacterSave save) {

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

	public PlayerAttack(int health, int mana, String weaponId, int team, Set<String> weaponTypes) {
		super(health, mana, weaponId, team, weaponTypes);
		lives = 3;
	}

	public PlayerAttack(PlayerAttack playerAttack) {
		this(playerAttack.maxHealth, playerAttack.maxMana, playerAttack.weaponId, 1,
				new HashSet<String>(playerAttack.weaponTypes));
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
package components.attack;

import java.util.Set;
import java.util.stream.Collectors;

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
	
	public PlayerAttack(int health, int mana, String weaponId, int team, String helmetId, String chestId, String legsId, String bootsId,
			Set<String> weaponTypes) {
		super(health, mana, weaponId, team, weaponTypes,helmetId,chestId,legsId,bootsId);
		lives = 3;
	}

	public PlayerAttack(PlayerAttack playerAttack) {
		this(playerAttack.maxHealth, playerAttack.maxMana, playerAttack.weaponId, playerAttack.team,playerAttack.helmetId, playerAttack.chestId, playerAttack.legsId, playerAttack.bootsId,
				playerAttack.weaponTypes.stream().collect(Collectors.toSet()));
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
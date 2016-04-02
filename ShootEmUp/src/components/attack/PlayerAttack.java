package components.attack;

import components.Message;
import display.Art;
import gui.menus.GameOverMenu;
import main.ShootEmUp;
import object.Armour;
import object.Entity;
import object.Weapon;
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
		weapon = new Weapon(save.getWeapon(), 0);
		if (save.getBoots() != null) {
			boots = new Armour(save.getBoots());
			setArmourValue();
		}
		if (save.getLegs() != null) {
			legs = new Armour(save.getLegs());
			setArmourValue();
		}
		if (save.getChest() != null) {
			chest = new Armour(save.getChest());
			setArmourValue();
		}
		if (save.getHelmet() != null) {
			helmet = new Armour(save.getHelmet());
			setArmourValue();
		}

		maxHealth = save.getMaxHealth();
		maxHealthRegen = save.getMaxHealthRegen();
		maxMana = save.getMaxMana();
		maxManaRegen = save.getMaxManaRegen();
	}
	
	public void die(Entity e){
		health = maxHealth;
		mana = maxMana;
		removeLife();
		e.send(Message.ENTITY_DIED);
	}

	public void removeLife() {
		if (lives == 1) {
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
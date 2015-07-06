package Components.Attack;

import Display.Art;
import GUI.Menus.GameOverMenu;
import Main.ShootEmUp;

public abstract class PlayerAttack extends BaseAttack {
	
	private int lives;
	protected int mana;
	protected int manaRegen;
	protected int maxMana;
	protected int maxManaRegen;
	
	public void removeLife() {
		if(lives == 1){
			ShootEmUp.paused = true;
			ShootEmUp.menuStack.add(new GameOverMenu(Art.gameOverScreen));
		} else {
			lives--;
		}
	}
	
	public void addHealth(int i){
		health += i;
		if(health > maxHealth){
			health = maxHealth;
		}
	}
	
	public void addMana(int i){
		mana += i;
		if(mana > maxMana){
			mana = maxMana;
		}
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMaxManaRegen() {
		return maxManaRegen;
	}

	public void setMaxManaRegen(int maxManaRegen) {
		this.maxManaRegen = maxManaRegen;
	}
}

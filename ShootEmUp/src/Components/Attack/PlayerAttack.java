  package Components.Attack;

import Components.Message;
import Components.Spawn.BaseSpawn;
import Display.Art;
import GUI.Menus.GameOverMenu;
import Main.ShootEmUp;
import Object.Entity;
import Object.Weapon;

public class PlayerAttack extends BaseAttack {
	protected TypeAttack type;
	
	protected int lives;
	protected int mana;
	protected int manaRegen;
	protected int maxMana;
	protected int maxManaRegen;
	
	public PlayerAttack(TypeAttack type){
		
	}
	
	public PlayerAttack(TypeAttack type, BaseSpawn BS, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana, int lives){
		this.BS = BS;
		this.setWeapon(weapon);
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		this.maxManaRegen = manaRegen;
		this.lives = lives;
		this.type = type;
		
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(getWeapon().isMelee()){
				getWeapon().attack(e, dir);
			}
			else if(mana >= getWeapon().getManaCost()){
				getWeapon().attack(e, dir);
				mana-=getWeapon().getManaCost();
			}
			fireRate = getWeapon().getFireRate();
		}
	}
	
	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			health = maxHealth;
			mana = maxMana;
			removeLife();
			BS.spawn(e);
		}
		
		healthRegen();
		
		if (manaRegen <= 0) {
			manaRegen = maxManaRegen;
			if (mana < maxMana) {
				mana++;
			}
		}
		manaRegen--;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
	
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

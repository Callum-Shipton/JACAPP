package Components.Attack;

import Components.Message;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public class MageAttack extends BaseAttack implements AttackComponent {

	BaseSpawn BS;
	
	protected int mana;
	protected int manaRegen;
	protected int maxMana;

	public MageAttack(BaseSpawn BS, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana){
		this.BS = BS;
		this.weapon = weapon;
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(weapon.isMelee()){
				weapon.attack(e, dir);
			}
			else if(mana >= weapon.getManaCost()){
				weapon.attack(e, dir);
				mana-=weapon.getManaCost();
			}
			fireRate = weapon.getFireRate();
		}
	}
	
	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			health = maxHealth;
			mana = maxMana;
			BS.spawn(e);
		}
		
		if (healthRegen <= 0) {
			healthRegen = 100;
			if (health < maxHealth) {
				maxHealth++;
			}
		}
		healthRegen--;
		
		if (manaRegen <= 0) {
			manaRegen = 50;
			if (mana < maxMana) {
				mana++;
			}
		}
		manaRegen--;
	}
	
	/*
	public boolean checkDead(Entity e) {
		if (BA.getHealth() <= 0) {
			e.setDestroy(true);
			ShootEmUp.currentLevel.eMap.removeEntity(BM.getGridPos(), e);
			
			//create exp
			Entity exp = new Entity();
			AnimatedGraphics expG = new AnimatedGraphics(Art.exp);
			PointSpawn expS = new PointSpawn(expG, new Vector2(AG.getX(),AG.getY()), exp);
			exp.addComponent(expG);
			exp.addComponent(expS);
			ShootEmUp.currentLevel.characters.add(exp);
			
			//create coins
			Entity coin = new Entity();
			AnimatedGraphics coinG = new AnimatedGraphics(Art.coin);
			PointSpawn coinS = new PointSpawn(coinG, new Vector2(AG.getX(),AG.getY()), coin);
			exp.addComponent(coinG);
			exp.addComponent(coinS);
			ShootEmUp.currentLevel.characters.add(coin);
			
			
			//create armour, item or weapon
			Random rand = new Random();
			int prob = rand.nextInt(3);
			if(prob == 0 ) {
				int armourProb = rand.nextInt(5);
				Entity armour = new Entity();
				AnimatedGraphics armourG;
				if(armourProb == 0){
					armourG = new AnimatedGraphics(Art.shoes);
				} else if(armourProb == 1){
					armourG = new AnimatedGraphics(Art.legs);
				} else if(armourProb == 2){
					armourG = new AnimatedGraphics(Art.chest);
				} else if(armourProb == 3){
					armourG = new AnimatedGraphics(Art.helmet);
				} else {
					armourG = new AnimatedGraphics(Art.ring);
				}
				PointSpawn armourS = new PointSpawn(armourG, new Vector2(AG.getX(),AG.getY()), armour);
				exp.addComponent(armourG);
				exp.addComponent(armourS);
				ShootEmUp.currentLevel.characters.add(armour);
			} else if(prob == 1) {
				int itemProb = rand.nextInt(4);
				Entity item = new Entity();
				AnimatedGraphics itemG;
				if(itemProb == 0) {
					itemG = new AnimatedGraphics(Art.healthPotion);
				} else if(itemProb == 1){
					itemG = new AnimatedGraphics(Art.manaPotion);
				} else if(itemProb == 2){
					itemG = new AnimatedGraphics(Art.speedPotion);
				} else {
					itemG = new AnimatedGraphics(Art.knockbackPotion);
				}
				PointSpawn itemS = new PointSpawn(itemG, new Vector2(AG.getX(),AG.getY()), item);
				exp.addComponent(itemG);
				exp.addComponent(itemS);
				ShootEmUp.currentLevel.characters.add(item);
			} else {	
				Entity weapon = new Entity();
				AnimatedGraphics weaponG = new AnimatedGraphics(Art.bow);
				PointSpawn weaponS = new PointSpawn(weaponG, new Vector2(AG.getX(),AG.getY()), weapon);
				exp.addComponent(weaponG);
				exp.addComponent(weaponS);
				ShootEmUp.currentLevel.characters.add(weapon);
			}
			return true;
		}
		return false;
	}
	*/

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
	
	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}

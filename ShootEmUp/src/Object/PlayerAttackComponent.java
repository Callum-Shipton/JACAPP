package Object;

public class PlayerAttackComponent implements AttackComponent {

	PlayerSpawnComponent PSC;
	
	private Weapon weapon = new Weapon(1, 10, 10, false, 1);
	private int fireRate = 10;
	private int health = 18;
	private int mana = 18;
	private int maxMana = 18;
	private int maxHealth = 18;
	
	public PlayerAttackComponent(PlayerSpawnComponent PSC){
		this.PSC = PSC;
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
			fireRate = weapon.getFirerate();
		}
	}
	
	public void update(Entity e){
		if(fireRate > 0)fireRate--;
		if(health <= 0) {
			PSC.spawn(e);
			health = getMaxHealth();
			mana = getMaxMana();
		}
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

}

package Components.Inventory;

public class PlayerInventory extends BasicInventory{
	
	private final int MAX_LEVEL = 99;
	private final int MAX_EXP_BOUND = 18;
	
	private int expBound;
	private int lives;
	
	public PlayerInventory(int level, int expBound, int lives) {
		super(level);
		this.expBound = expBound;
		this.lives = lives;
	}
	
	public int getExpBound() {
		return expBound;
	}

	public void setExpBound(int expBound) {
		this.expBound = expBound;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void giveExp(int exp){
		this.exp += exp;
		if(this.exp > expBound){
			if(level < MAX_LEVEL){
				this.exp = 0;
				level++;
				if (expBound < MAX_EXP_BOUND){
					expBound++;
				}		
			}
		}
	}
}

package Components.Inventory;

public class PlayerInventory extends BasicInventory{
	
	private int expBound;
	protected int lives;
	
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
}

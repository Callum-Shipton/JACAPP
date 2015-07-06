package Components.Inventory;

import Components.Attack.PlayerAttack;

public class PlayerInventory extends BasicInventory{
	
	private final int MAX_LEVEL = 99;
	private final int MAX_EXP_BOUND = 18;
	
	private int expBound;
	
	private PlayerAttack PA;
	
	public PlayerInventory(PlayerAttack PA, int level, int expBound) {
		super(level);
		this.PA = PA;
		this.expBound = expBound;
	}
	
	public void giveItem(PickupType type, Subtype subtype){
		switch(type){
		case COIN: 
			if(coins < 99){
				coins++;
			}
			break;
		case POTION:
			PotionType potionType = (PotionType) subtype;
			switch(potionType){
			case HEALTH:
				PA.addHealth(5);
				break;
			case MANA:
				PA.addMana(5);
				break;
			case SPEED:
				break;
			case KNOCKBACK:
			}
			break;
		case ARMOUR:
			break;
		case WEAPON:
		}
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
	
	public int getExpBound() {
		return expBound;
	}

	public void setExpBound(int expBound) {
		this.expBound = expBound;
	}
}

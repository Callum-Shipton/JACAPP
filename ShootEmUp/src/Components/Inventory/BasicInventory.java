package Components.Inventory;

import java.util.HashSet;

import Object.Entity;

public class BasicInventory extends BaseInventory{

	public BasicInventory(int level, int exp, int coins){
		this.level = level;
		this.exp = exp;
		this.coins = coins;
		
		equiped = new HashSet<Entity>();
	}
	
	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}

}

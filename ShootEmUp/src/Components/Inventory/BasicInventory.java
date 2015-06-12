package Components.Inventory;

import java.util.HashSet;

import Object.Entity;

public class BasicInventory extends BaseInventory{

	public BasicInventory(int level){
		this.level = level;
		exp = 0;
		coins = 0;
		
		equiped = new HashSet<Entity>();
	}
	
	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}

}

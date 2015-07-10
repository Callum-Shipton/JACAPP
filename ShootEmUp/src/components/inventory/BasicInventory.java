package components.inventory;

import java.util.HashSet;

import entities.Entity;

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

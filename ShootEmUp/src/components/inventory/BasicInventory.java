package components.inventory;

import java.util.HashSet;

import object.Entity;

public class BasicInventory extends BaseInventory{

	public BasicInventory(int level){
		this.level = level;
		exp = 0;
		coins = 0;
		
		equipped = new HashSet<Entity>();
	}
	
	@Override
	public void update(Entity e) {
		
	}

}

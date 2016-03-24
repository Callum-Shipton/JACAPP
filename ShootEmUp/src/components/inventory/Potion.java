package components.inventory;

import object.Entity;

public abstract class Potion {

	protected TypePotion type;
	
	protected int quantity = 0;
	
	protected boolean active = false;
	
	Potion(TypePotion type){
		this.type = type;
	}
	
	abstract void update(Entity e);
	
	void usePotion(){
			if(quantity > 0 ){
				active = true;
				quantity--;
			}
	}
	
	void addPotion(){
		quantity++;
	}
}

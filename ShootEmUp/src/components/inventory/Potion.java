package components.inventory;

import java.io.Serializable;

import object.Entity;

public abstract class Potion implements Serializable {

	private static final long serialVersionUID = -3226423799063342754L;

	protected TypePotion type;
	
	protected int quantity = 1;
	
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

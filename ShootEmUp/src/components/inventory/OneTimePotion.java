package components.inventory;

import components.TypeComponent;
import components.attack.BaseAttack;
import object.Entity;

public class OneTimePotion extends Potion {

	OneTimePotion(TypePotion type){
		super(type);
	}
	
	@Override
	void update(Entity e) {
		if(active){
			BaseAttack BA = (BaseAttack) e.getComponent(TypeComponent.ATTACK);
			switch (type) {
			case HEALTH:
					BA.addHealth(5);
					active = false;
					break;
					
			case MANA:
				BA.addMana(5);
				active = false;
				break;
				
			default:
				System.err.println("Invalid Potion Type");
				break;
			}
		}
	}



}

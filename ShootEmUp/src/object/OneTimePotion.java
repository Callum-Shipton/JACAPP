package object;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.inventory.TypePotion;

public class OneTimePotion extends Potion {

	private static final long serialVersionUID = -5999484039194013016L;

	public OneTimePotion(TypePotion type){
		super(type);
	}
	
	@Override
	public void update(Entity e) {
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

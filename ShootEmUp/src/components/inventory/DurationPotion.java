package components.inventory;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.movement.BaseMovement;
import object.Entity;

public class DurationPotion extends Potion {

	private int duration;
	private int maxDuration;
	
	DurationPotion(TypePotion type, int maxDuration){
		super(type);
		this.maxDuration = maxDuration;
	}
	
	@Override
	void update(Entity e) {
		if(active){
			BaseMovement BM = (BaseMovement) e.getComponent(TypeComponent.MOVEMENT);
			BaseAttack BA = (BaseAttack) e.getComponent(TypeComponent.ATTACK);
			switch(type){
			case SPEED:
				if(duration == maxDuration){
					BM.increaseSpeed(2);
					}
				else if(duration == 0){
					BM.increaseSpeed(-2);
				}
				break;
			case KNOCKBACK:
				if(duration == maxDuration){
					//Code for adding to knockback
					BA.setHealth(100);
					BA.setMaxHealth(100);
					BA.setMana(100);
					BA.setMaxMana(100);
				}
				break;
				
			default:
				System.err.println("Invalid Potion Type");
				break;
			}
			duration--;
			if(duration < 0){
				active = false;
			}
		}
	
	}
	
	@Override
	void usePotion(){
		if(quantity > 0 ){
			active = true;
			duration = maxDuration;
			quantity--;
		}
	}
}
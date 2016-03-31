package object;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.inventory.TypePotion;
import components.movement.BaseMovement;
import main.ShootEmUp;

public class DurationPotion extends Potion {

	private static final long serialVersionUID = 1435723715378911832L;
	
	private int duration;
	private int maxDuration;
	private int counter = 0;
	
	
	public DurationPotion(TypePotion type, int maxDuration){
		super(type);
		this.maxDuration = maxDuration;
	}
	
	@Override
	public void update(Entity e) {
		if(active && counter == 0){
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
	counter++;
	if(counter == (int) ShootEmUp.FPS){
		counter = 0;
	}
	}
	
	@Override
	public void usePotion(){
		if(quantity > 0 ){
			if(active){
				duration = maxDuration-1;
			}
			else{
			active = true;
			duration = maxDuration;
			}
			quantity--;
			counter = 0;
		}
	}
}
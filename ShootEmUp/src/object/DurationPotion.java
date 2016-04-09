package object;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.movement.BaseMovement;
import math.Seconds;

public class DurationPotion extends Potion {

	private static final long serialVersionUID = 1435723715378911832L;

	private int duration;
	private int maxDuration;
	private int counter = 0;

	public DurationPotion(String type, int maxDuration) {
		super(type);
		this.maxDuration = maxDuration;
	}

	@Override
	public void update(Entity e) {
		if (this.active && (this.counter == 0)) {
			BaseMovement BM = e.getComponent(TypeComponent.MOVEMENT);
			BaseAttack BA = e.getComponent(TypeComponent.ATTACK);
			switch (this.type) {
			case "Speed":
				if (this.duration == this.maxDuration) {
					BM.increaseSpeed(2);
				} else if (this.duration == 0) {
					BM.increaseSpeed(-2);
				}
				break;
			case "Knockback":
				if (this.duration == this.maxDuration) {
					// Code for adding to knockback
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
			this.duration--;
			if (this.duration < 0) {
				this.active = false;
			}
		}
		this.counter++;
		if (this.counter == Seconds.ticks(1)) {
			this.counter = 0;
		}
	}

	@Override
	public void usePotion() {
		if (this.quantity > 0) {
			if (this.active) {
				this.duration = this.maxDuration - 1;
			} else {
				this.active = true;
				this.duration = this.maxDuration;
			}
			this.quantity--;
			this.counter = 0;
		}
	}
}
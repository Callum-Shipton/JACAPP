package object;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.movement.BaseMovement;
import entity.Entity;
import logging.Logger;
import loop.GameLoop;

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
		if (active && (counter == 0)) {
			BaseMovement playerMovement = e.getComponent(TypeComponent.MOVEMENT);
			BaseAttack playerAttack = e.getComponent(TypeComponent.ATTACK);
			switch (type) {
			case "Speed":
				if (duration == maxDuration) {
					playerMovement.increaseSpeed(2);
				} else if (duration == 0) {
					playerMovement.increaseSpeed(-2);
				}
				break;
			case "Knockback":
				if (duration == maxDuration) {
					// Code for adding to knockback
					playerAttack.setHealth(100);
					playerAttack.setMaxHealth(100);
					playerAttack.setMana(100);
					playerAttack.setMaxMana(100);
				}
				break;

			default:
				Logger.warn("Invalid Potion Type");
				break;
			}
			duration--;
			if (duration < 0) {
				active = false;
			}
		}
		counter++;
		if (counter == GameLoop.ticks(1)) {
			counter = 0;
		}
	}

	@Override
	public void usePotion() {
		if (quantity > 0) {
			if (active) {
				duration = maxDuration - 1;
			} else {
				active = true;
				duration = maxDuration;
			}
			quantity--;
			counter = 0;
		}
	}
}
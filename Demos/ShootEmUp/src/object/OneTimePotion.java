package object;

import components.TypeComponent;
import components.attack.BaseAttack;
import entity.Entity;
import logging.Logger;

public class OneTimePotion extends Potion {

	private static final long serialVersionUID = -5999484039194013016L;

	public OneTimePotion(String type) {
		super(type);
	}

	@Override
	public void update(Entity e) {
		if (this.active) {
			BaseAttack BA = e.getComponent(TypeComponent.ATTACK);
			switch (this.type) {
			case "Health":
				BA.addHealth(5);
				this.active = false;
				break;

			case "Mana":
				BA.addMana(5);
				this.active = false;
				break;

			default:
				Logger.warn("Invalid Potion Type");
				break;
			}
		}
	}

}

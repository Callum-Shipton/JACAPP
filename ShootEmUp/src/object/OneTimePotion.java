package object;

import components.TypeComponent;
import components.attack.BaseAttack;

public class OneTimePotion extends Potion {

	private static final long serialVersionUID = -5999484039194013016L;

	public OneTimePotion(String type) {
		super(type);
	}

	@Override
	public void update(Entity e) {
		if (active) {
			BaseAttack BA = e.getComponent(TypeComponent.ATTACK);
			switch (type) {
				case "Health":
					BA.addHealth(5);
					active = false;
					break;

				case "Mana":
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

package object;

import components.inventory.TypeArmour;
import display.Art;

public abstract class ArmourBuilder {

	public static Armour buildArmour(TypeArmour type) {
		switch (type) {
			case BOOTS:
				return new Armour(type, 2, Art.getImage("LeatherBootsButton"));
			case LEGS:
				return new Armour(type, 5, Art.getImage("LeatherLegsButton"));
			case CHESTPLATE:
				return new Armour(type, 10, Art.getImage("LeatherChestButton"));
			case HELMET:
				return new Armour(type, 7, Art.getImage("LeatherHelmetButton"));
			default:
				System.out.println("no armour");
		}
		return null;
	}
}

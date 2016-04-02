package object;

import components.inventory.TypeArmour;

public abstract class ArmourBuilder {

	public static Armour buildArmour(TypeArmour type) {
		switch (type) {
			case BOOTS:
				return new Armour(type, 2, "LeatherBootsButton");
			case LEGS:
				return new Armour(type, 5, "LeatherLegsButton");
			case CHESTPLATE:
				return new Armour(type, 10, "LeatherChestButton");
			case HELMET:
				return new Armour(type, 7, "LeatherHelmetButton");
			default:
				System.out.println("no armour");
		}
		return null;
	}
}

package object;

import components.inventory.TypeArmour;
import display.Art;

public abstract class ArmourBuilder {
	public static Armour buildArmour(TypeArmour type){
		switch(type){
		case BOOTS:
			return new Armour(type, 2, Art.bootsButton);
		case LEGS:
			return new Armour(type, 5, Art.legsButton);
		case CHESTPLATE:
			return new Armour(type, 10, Art.chestButton);
		case HELMET:
			return new Armour(type, 7, Art.helmetButton);
		default:
			System.out.println("no armour");
		}
		return null;
	}
}

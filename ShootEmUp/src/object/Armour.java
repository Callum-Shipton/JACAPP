package object;

import components.inventory.TypeArmour;
import components.inventory.TypePickup;
import display.Image;

public class Armour extends InventoryItem {

	private TypeArmour type;
	private int defence;

	public Armour(TypeArmour type, int defence, Image inventoryImage) {
		this.type = type;
		this.defence = defence;
		this.inventoryImage = inventoryImage;
		typePickup = TypePickup.ARMOUR;
	}

	public int getDefence() {
		return defence;
	}

	public TypeArmour getType() {
		return type;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

}

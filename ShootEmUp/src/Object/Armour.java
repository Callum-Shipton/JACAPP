package Object;

import Components.Inventory.TypeArmour;
import Display.Image;

public class Armour extends InventoryItem{
	
	private TypeArmour type;
	private int defence;
	
	public Armour(TypeArmour type, int defence, Image inventoryImage){
		this.type = type;
		this.defence = defence;
		this.inventoryImage = inventoryImage;
	}
	
	public int getDefence(){
		return defence;
	}

	public TypeArmour getType() {
		return type;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}
	
}

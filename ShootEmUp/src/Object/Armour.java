package Object;

import Components.Inventory.ArmourType;
import Display.Image;

public class Armour extends InventoryItem{
	
	private ArmourType type;
	private int defence;
	
	public Armour(ArmourType type, int defence, Image inventoryImage){
		this.type = type;
		this.defence = defence;
		this.inventoryImage = inventoryImage;
	}
	
	public int getDefence(){
		return defence;
	}

	public ArmourType getType() {
		return type;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}
	
}

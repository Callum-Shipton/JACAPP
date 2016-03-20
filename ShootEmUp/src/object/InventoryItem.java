package object;

import components.inventory.TypePickup;
import display.Image;

public class InventoryItem{
	protected Image inventoryImage;
	protected TypePickup typePickup;
	
	public Image getInventoryImage(){
		return inventoryImage;
	}
	
	public TypePickup getTypePickup(){
		return typePickup;
	}
}

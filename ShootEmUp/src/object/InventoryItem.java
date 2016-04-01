package object;

import components.inventory.TypePickup;
import display.Art;
import display.Image;

public class InventoryItem {

	protected String inventoryImage;
	protected transient TypePickup typePickup;

	public Image getInventoryImage() {
		return Art.getImage(inventoryImage);
	}

	public TypePickup getTypePickup() {
		return typePickup;
	}
}

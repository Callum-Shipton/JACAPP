package object;

import com.google.gson.Gson;

import components.inventory.TypePickup;
import display.Art;
import display.Image;

public class InventoryItem {

	
	protected static Gson g;
	protected String inventoryImage;
	protected transient TypePickup typePickup;

	public Image getInventoryImage() {
		return Art.getImage(inventoryImage);
	}

	public TypePickup getTypePickup() {
		return typePickup;
	}
}

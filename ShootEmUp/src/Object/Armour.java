package Object;

import Components.Inventory.ArmourType;
import Display.Image;

public class Armour extends InventoryItem{
	
	private ArmourType type;
	private int defence;
	private Image image;
	
	public Armour(ArmourType type, int defence, Image image){
		this.type = type;
		this.defence = defence;
		this.image = image;
	}
	
	public int getDefence(){
		return defence;
	}
}

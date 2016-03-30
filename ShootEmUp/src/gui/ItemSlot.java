package gui;

import java.util.ArrayList;

import components.inventory.TypePickup;
import display.Art;
import display.DPDTRenderer;
import display.Image;
import object.Armour;
import object.InventoryItem;
import object.Weapon;

public class ItemSlot extends GuiComponent{
	private Icon icon;
	private ArrayList<Counter> stats = new ArrayList<Counter>();
	private int BUFFER = 5; 
	
	public ItemSlot(int x, int y, InventoryItem item){
		super(x, y);
		int gap = 0;
		icon = new Icon(x, y, item.getInventoryImage(), false, 1.0f);
		gap += icon.getSize().x() + BUFFER;
		if(item.getTypePickup() == TypePickup.ARMOUR){
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), Art.armourIcon, false, ((Armour) item).getDefence(), 0.5f));
		} else if(item.getTypePickup() == TypePickup.WEAPON){
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), Art.damageIcon, false, ((Weapon) item).getDamage(), 0.5f));
			gap += stats.get(0).getFullSize().x() + BUFFER;
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), Art.rangeIcon, false, ((Weapon) item).getRange(), 0.5f));
			gap += stats.get(1).getFullSize().x() + BUFFER;
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), Art.fireRateIcon, false, ((Weapon) item).getFireRate(), 0.5f));
			gap += stats.get(2).getFullSize().x() + BUFFER;
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), Art.manaCostIcon, false, ((Weapon) item).getManaCost(), 0.5f));
		}
	}
	
	public void updateImage(Image image){
		icon.setI(image);
	}
	
	public void updateStats(int[] nums){
		int count = 0;
		for(Counter counter: stats){
			counter.update(nums[count]);
			count++;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(DPDTRenderer d) {
		icon.render(d);
		for(Counter counter: stats){
			counter.render(d);
		}
	}
}

package gui;

import java.util.ArrayList;

import components.inventory.TypePickup;
import display.Art;
import display.DPDTRenderer;
import display.Image;
import object.Armour;
import object.InventoryItem;
import object.Weapon;

public class ItemSlot extends GuiComponent {

	private Icon icon;
	private ArrayList<Counter> stats = new ArrayList<Counter>();
	private int BUFFER = 5;

	public ItemSlot(int x, int y, InventoryItem<?> item) {
		super(x, y);
		int gap = 0;
		this.icon = new Icon(x, y, item.getInventoryImage(), false, 1.0f);
		gap += this.icon.getSize().x() + this.BUFFER;
		if (item.getTypePickup() == TypePickup.ARMOUR) {
			this.stats.add(new Counter(x + gap, y + (this.icon.getSize().y() / 4), Art.getImage("ArmourIcon"), false,
					((Armour) item).getDefence(), 0.5f));
		} else if (item.getTypePickup() == TypePickup.WEAPON) {
			this.stats.add(new Counter(x + gap, y + (this.icon.getSize().y() / 4), Art.getImage("DamageIcon"), false,
					((Weapon) item).getDamage(), 0.5f));
			gap += this.stats.get(0).getFullSize().x() + this.BUFFER;
			this.stats.add(new Counter(x + gap, y + (this.icon.getSize().y() / 4), Art.getImage("RangeIcon"), false,
					((Weapon) item).getRange(), 0.5f));
			gap += this.stats.get(1).getFullSize().x() + this.BUFFER;
			this.stats.add(new Counter(x + gap, y + (this.icon.getSize().y() / 4), Art.getImage("FireRateIcon"), false,
					((Weapon) item).getFireRate(), 0.5f));
			gap += this.stats.get(2).getFullSize().x() + this.BUFFER;
			this.stats.add(new Counter(x + gap, y + (this.icon.getSize().y() / 4), Art.getImage("ManaCostIcon"), false,
					((Weapon) item).getManaCost(), 0.5f));
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		this.icon.render(d);
		for (Counter counter : this.stats) {
			counter.render(d);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public void updateImage(Image image) {
		this.icon.setI(image);
	}

	public void updateStats(int[] nums) {
		int count = 0;
		for (Counter counter : this.stats) {
			counter.update(nums[count]);
			count++;
		}
	}
}

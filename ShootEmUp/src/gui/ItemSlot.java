package gui;

import java.util.ArrayList;
import java.util.List;

import components.inventory.TypePickup;
import display.DPDTRenderer;
import display.Image;
import display.ImageProcessor;
import object.Armour;
import object.InventoryItem;
import object.Weapon;

public class ItemSlot extends GuiComponent {

	private Icon icon;
	private List<Counter> stats = new ArrayList<>();
	private int buffer = 5;

	public ItemSlot(int x, int y, InventoryItem<?> item) {
		super(x, y);
		int gap = 0;
		icon = new Icon(x, y, item.getInventoryImage(), false, 1.0f);
		gap += icon.getSize().x() + buffer;
		if (item.getTypePickup() == TypePickup.ARMOUR) {
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), ImageProcessor.getImage("ArmourIcon"), false,
					((Armour) item).getDefence(), 0.5f));
		} else if (item.getTypePickup() == TypePickup.WEAPON) {
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), ImageProcessor.getImage("DamageIcon"), false,
					((Weapon) item).getDamage(), 0.5f));
			gap += stats.get(0).getFullSize().x() + buffer;
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), ImageProcessor.getImage("RangeIcon"), false,
					((Weapon) item).getRange(), 0.5f));
			gap += stats.get(1).getFullSize().x() + buffer;
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), ImageProcessor.getImage("FireRateIcon"), false,
					((Weapon) item).getFireRate(), 0.5f));
			gap += stats.get(2).getFullSize().x() + buffer;
			stats.add(new Counter(x + gap, y + (icon.getSize().y() / 4), ImageProcessor.getImage("ManaCostIcon"), false,
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
		/**
		 * No update needed
		 */
	}

	public void updateImage(Image image) {
		icon.setI(image);
	}

	public void updateStats(int[] nums) {
		int count = 0;
		for (Counter counter : stats) {
			counter.update(nums[count]);
			count++;
		}
	}
}

package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.DPDTRenderer;
import display.Image;
import display.ImageProcessor;
import main.ShootEmUp;
import object.InventoryItem;

public class Inventory extends GuiComponent {

	private List<InventoryItem<?>> i;
	private List<GuiComponent> items = new ArrayList<>();
	private boolean itemEquipped = false;

	public Inventory(int x, int y, List<InventoryItem<?>> i) {
		super(x, y);
		this.i = i;
		refreshItemGrid();
	}

	private GuiComponent addItem(GuiComponent item) {
		items.add(item);
		return item;
	}

	private void refreshItemGrid() {
		int row = 0;
		int column = 0;

		Iterator<InventoryItem<?>> itemsIter = i.iterator();
		int count = 0;

		while (itemsIter.hasNext()) {

			InventoryItem<?> item = itemsIter.next();
			Image image = item.getInventoryImage();

			Button invItemButton = new Button(image, new EquipItemButton(count++));
			ItemSlot invItemHover = new ItemSlot((int) x + (image.getWidth() * row),
					(int) y + ((image.getHeight() / 2) * column), item);
			HoverButton invItem = new HoverButton(x + (image.getWidth() * row), y + ((image.getHeight() / 2) * column),
					invItemButton, invItemHover);

			addItem(invItem);
			row++;
			if (row > 10) {
				row = 0;
				column++;
			}
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		for (GuiComponent item : items) {
			item.render(ImageProcessor.stat);
		}
	}

	@Override
	public void update() {
		for (GuiComponent item : items) {
			item.update();
		}
		if (itemEquipped) {
			items.clear();
			refreshItemGrid();
			itemEquipped = false;
		}
	}

	private class EquipItemButton implements ButtonAction {

		private int position;

		public EquipItemButton(int position) {
			this.position = position;
		}

		@Override
		public void click() {
			BaseInventory bi = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
			bi.equipItem(position);
			itemEquipped = true;
		}
	}
}

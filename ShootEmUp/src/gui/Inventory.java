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
	private List<MenuButton> buttons = new ArrayList<>();
	private boolean itemEquipped = false;

	public Inventory(int x, int y, List<InventoryItem<?>> i) {
		super(x, y);
		this.i = i;
		addButtons();
	}

	private MenuButton addButton(MenuButton button) {
		buttons.add(button);
		return button;
	}

	private void addButtons() {
		int row = 0;
		int column = 0;

		Iterator<InventoryItem<?>> items = i.iterator();
		int count = 0;

		while (items.hasNext()) {

			InventoryItem<?> item = items.next();
			Image image = item.getInventoryImage();
			addButton(new MenuButton(image, x + (image.getWidth() * row), y + ((image.getHeight() / 2) * column),
					new EquipItemButton(count++)));
			row++;
			if (row > 10) {
				row = 0;
				column++;
			}
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		for (MenuButton button : buttons) {
			button.render(ImageProcessor.stat);
		}
	}

	@Override
	public void update() {
		for (MenuButton button : buttons) {
			button.update();
		}
		if (itemEquipped) {
			buttons.clear();
			addButtons();
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

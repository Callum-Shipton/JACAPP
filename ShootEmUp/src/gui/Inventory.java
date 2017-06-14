package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Art;
import display.DPDTRenderer;
import main.ShootEmUp;
import object.InventoryItem;

public class Inventory extends GuiComponent {

	private List<InventoryItem<?>> i;
	private List<MenuButton> buttons = new ArrayList<>();

	public Inventory(int x, int y, List<InventoryItem<?>> i) {
		super(x, y);
		this.i = i;
		addButtons();
	}

	private MenuButton addButton(MenuButton button) {
		this.buttons.add(button);
		return button;
	}

	private void addButtons() {
		int row = 0;
		int column = 0;

		Iterator<InventoryItem<?>> items = i.iterator();

		while (items.hasNext()) {
			InventoryItem<?> item = items.next();
			addButton(new MenuButton(TypeButton.OTHER, item.getInventoryImage(),
					x + (item.getInventoryImage().getWidth() * row),
					y + ((item.getInventoryImage().getHeight() / 2) * column)));
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
			button.render(Art.stat);
		}
	}

	@Override
	public void update() {
		for (MenuButton button : buttons) {
			button.update();
		}
		Iterator<MenuButton> buttonsIterator = buttons.iterator();
		MenuButton itemButton;
		boolean change = false;
		int position = 0;
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		while (buttonsIterator.hasNext()) {
			itemButton = buttonsIterator.next();
			if (itemButton.hasClicked()) {
				BI.equipItem(position);
				itemButton.postAction();
				buttonsIterator.remove();
				change = true;
			}

			position++;
		}

		if (change) {
			buttons.clear();
			addButtons();
		}

	}
}

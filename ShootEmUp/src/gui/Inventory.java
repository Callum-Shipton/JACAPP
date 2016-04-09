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

	private int row = 0;
	private int column = 0;

	private ArrayList<InventoryItem> i;
	private List<Button> buttons = new ArrayList<Button>();

	public Inventory(int x, int y, ArrayList<InventoryItem> i) {
		super(x, y);
		this.i = i;
		addButtons();
	}

	private void addButtons() {
		row = 0;
		column = 0;

		Iterator<InventoryItem> items = i.iterator();

		while (items.hasNext()) {
			InventoryItem item = items.next();
			addButton(new Button(TypeButton.OTHER, item.getInventoryImage(),
					x + ((item.getInventoryImage().getWidth() * row)),
					y + (((item.getInventoryImage().getHeight() / 2) * column))));
			row++;
			if (row > 10) {
				row = 0;
				column++;
			}
		}
	}

	@Override
	public void update() {
		for (Button button : buttons) {
			button.update();
		}
		Iterator<Button> Buttons = buttons.iterator();
		Button itemButton;
		boolean change = false;
		int position = 0;
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		while (Buttons.hasNext()) {
			itemButton = Buttons.next();
			if (itemButton.hasClicked()) {
				BI
						.equipItem(position);
				itemButton.postAction();
				Buttons.remove();
				change = true;
			}

			position++;
		}

		if (change == true) {
			buttons.clear();
			addButtons();
		}

	}

	@Override
	public void render(DPDTRenderer d) {
		for (Button button : buttons) {
			button.render(Art.stat);
		}
	}

	private Button addButton(Button button) {
		buttons.add(button);
		return button;
	}
}

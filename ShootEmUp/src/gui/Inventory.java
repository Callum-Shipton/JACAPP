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

	private List<InventoryItem<?>> i;
	private List<Button> buttons = new ArrayList<>();

	public Inventory(int x, int y, List<InventoryItem<?>> i) {
		super(x, y);
		this.i = i;
		addButtons();
	}

	private Button addButton(Button button) {
		this.buttons.add(button);
		return button;
	}

	private void addButtons() {
		this.row = 0;
		this.column = 0;

		Iterator<InventoryItem<?>> items = this.i.iterator();

		while (items.hasNext()) {
			InventoryItem<?> item = items.next();
			addButton(new Button(TypeButton.OTHER, item.getInventoryImage(),
					this.x + ((item.getInventoryImage().getWidth() * this.row)),
					this.y + (((item.getInventoryImage().getHeight() / 2) * this.column))));
			this.row++;
			if (this.row > 10) {
				this.row = 0;
				this.column++;
			}
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		for (Button button : this.buttons) {
			button.render(Art.stat);
		}
	}

	@Override
	public void update() {
		for (Button button : this.buttons) {
			button.update();
		}
		Iterator<Button> Buttons = this.buttons.iterator();
		Button itemButton;
		boolean change = false;
		int position = 0;
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		while (Buttons.hasNext()) {
			itemButton = Buttons.next();
			if (itemButton.hasClicked()) {
				BI.equipItem(position);
				itemButton.postAction();
				Buttons.remove();
				change = true;
			}

			position++;
		}

		if (change == true) {
			this.buttons.clear();
			addButtons();
		}

	}
}

package gui;

import java.util.ArrayList;

import display.Art;
import display.DPDTRenderer;

public class ButtonList extends GuiComponent {

	ArrayList<GuiComponent> menuItems = new ArrayList<GuiComponent>();
	float gap;
	float height;

	public ButtonList(float x, float y, float height, float gap) {
		super(x, y);
		this.gap = gap;
		this.height = height;
	}

	public void addMenuItem(GuiComponent menuItem) {
		menuItem.setX(x);
		menuItem.setY(y + ((gap + height) * menuItems.size()));
		menuItems.add(menuItem);
	}

	@Override
	public void update() {
		for (GuiComponent menuItem : menuItems) {
			menuItem.update();
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		for (GuiComponent menuItem : menuItems) {
			menuItem.render(Art.stat);
		}
	}
}

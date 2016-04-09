package gui;

import java.util.ArrayList;

import display.Art;
import display.DPDTRenderer;

public class VerticalLayout extends GuiComponent {

	private ArrayList<GuiComponent> menuItems = new ArrayList<GuiComponent>();
	private float gap;
	private float height;

	public VerticalLayout(float x, float y, float height, float gap) {
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

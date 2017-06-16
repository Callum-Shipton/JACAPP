package gui;

import java.util.ArrayList;

import display.ImageProcessor;
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
		menuItem.setX(this.x);
		menuItem.setY(this.y + ((this.gap + this.height) * this.menuItems.size()));
		this.menuItems.add(menuItem);
	}

	@Override
	public void render(DPDTRenderer d) {
		for (GuiComponent menuItem : this.menuItems) {
			menuItem.render(ImageProcessor.stat);
		}
	}

	@Override
	public void update() {
		for (GuiComponent menuItem : this.menuItems) {
			menuItem.update();
		}
	}
}

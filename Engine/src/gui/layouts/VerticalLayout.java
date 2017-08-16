package gui.layouts;

import java.util.ArrayList;

import display.DPDTRenderer;
import display.BaseRenderSystem;
import gui.GuiComponent;

public class VerticalLayout extends GuiComponent {

	private ArrayList<GuiComponent> menuItems = new ArrayList<>();
	private final float gap;

	public VerticalLayout(float x, float y, float gap) {
		super(x, y);
		this.gap = gap;
	}

	public void addMenuItem(GuiComponent menuItem) {
		menuItem.setX(x);
		if (getHeight() == 0) {
			menuItem.setY(y + getHeight());
			menuItems.add(menuItem);
		} else {
			menuItem.setY(y + getHeight() + gap);
			menuItems.add(menuItem);
		}

		for (GuiComponent item : menuItems) {
			if (item.getWidth() > width) {
				width = item.getWidth();
			}
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		for (GuiComponent menuItem : menuItems) {
			menuItem.render(BaseRenderSystem.stat);
		}
	}

	@Override
	public void update() {
		for (GuiComponent menuItem : menuItems) {
			menuItem.update();
		}
	}

	@Override
	public float getHeight() {
		float height = 0.0f;

		for (GuiComponent component : menuItems) {
			height += component.getHeight();
		}

		height += gap * (menuItems.size() - 1);

		return height;
	}
}

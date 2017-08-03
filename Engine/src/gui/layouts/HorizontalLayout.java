package gui.layouts;

import java.util.ArrayList;

import display.DPDTRenderer;
import display.ImageProcessor;
import gui.GuiComponent;

public class HorizontalLayout extends GuiComponent {
	private ArrayList<GuiComponent> menuItems = new ArrayList<>();
	private final float gap;

	public HorizontalLayout(float x, float y, float gap) {
		super(x, y);
		this.gap = gap;
	}

	public HorizontalLayout(float gap) {
		this(0, 0, gap);
	}

	public void additem(GuiComponent menuItem) {
		menuItem.setX(x + getWidth() + gap);
		menuItem.setY(y);
		menuItems.add(menuItem);

		width += menuItems.get(menuItems.size() - 1).getWidth();

		for (GuiComponent item : menuItems) {
			if (item.getHeight() > height) {
				height = item.getHeight();
			}
		}
	}

	@Override
	public void render(DPDTRenderer d) {
		for (GuiComponent menuItem : menuItems) {
			menuItem.render(ImageProcessor.stat);
		}
	}

	@Override
	public void update() {
		for (GuiComponent menuItem : menuItems) {
			menuItem.update();
		}
	}

	@Override
	public float getWidth() {
		float width = 0.0f;

		for (GuiComponent component : menuItems) {
			width += component.getWidth();
		}

		width += gap * (menuItems.size() - 1);

		return width;
	}
}

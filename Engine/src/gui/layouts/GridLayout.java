package gui.layouts;

import java.util.ArrayList;

import display.DPDTRenderer;
import display.ImageProcessor;
import gui.GuiComponent;

public class GridLayout extends GuiComponent {

	private final int rows;
	private final int columns;
	private final float gap;

	private int row;
	private int column;

	private boolean full = false;

	private ArrayList<GuiComponent> gridItems = new ArrayList<>();

	public GridLayout(float x, float y, int rows, int columns, float gap) {
		super(x, y);
		this.rows = rows;
		this.columns = columns;
		this.gap = gap;
	}

	public void addComponent(GuiComponent component) {
		if (!full) {
			gridItems.add(component);
			if (gridItems.size() >= rows * columns) {
				full = true;
			}
		}
	}

	public void removeComponent(GuiComponent component) {
		gridItems.remove(component);
	}

	@Override
	public void render(DPDTRenderer d) {
		for (GuiComponent item : gridItems) {
			item.render(ImageProcessor.stat);
		}
	}

	@Override
	public void update() {
		for (GuiComponent item : gridItems) {
			item.update();
		}
	}

}

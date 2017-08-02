package gui;

import display.DPDTRenderer;

public class GridLayout extends GuiComponent {

	private final int rows;
	private final int columns;
	private final float gap;

	private int row = 0;
	private int column = 0;
	private boolean full = false;

	private final GuiComponent[][] grid;

	public GridLayout(float x, float y, int rows, int columns, float gap) {
		super(x, y);
		this.rows = rows;
		this.columns = columns;
		this.gap = gap;

		grid = new GuiComponent[rows][columns];
	}

	public void addComponent(GuiComponent component) {
		if (!full) {
			grid[row++][column] = component;
			if (row >= rows) {
				row = 0;
				column++;
				if (column >= columns) {
					full = true;
				}
			}
		}
	}

	@Override
	public void render(DPDTRenderer d) {
	}

	@Override
	public void update() {
	}

}

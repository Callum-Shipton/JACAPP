package gui;

import display.DPDTRenderer;
import display.Image;

public class CounterButton extends GuiComponent {

	private Button button;
	private Counter counter;

	public CounterButton(float x, float y, TypeButton type, Image image, int count, float scale) {
		super(x, y);
		button = ButtonBuilder.buildButton(type, x, y);
		counter = new Counter(x + button.getId().getWidth(), y, image, false, count, scale);
	}

	@Override
	public void update() {
		button.update();
	}

	@Override
	public void render(DPDTRenderer d) {
		button.render(d);
		counter.render(d);
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		button.setX(x);
		counter.setX(x + button.getId().getWidth());
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		button.setY(y);
		counter.setY(y);
	}
}

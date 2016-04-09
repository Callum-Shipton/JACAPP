package gui;

import display.DPDTRenderer;
import display.Image;

public class CounterButton extends GuiComponent {

	private Button button;
	private Counter counter;

	public CounterButton(float x, float y, TypeButton type, Image image, int count, float scale) {
		super(x, y);
		this.button = ButtonBuilder.buildButton(type, x, y);
		this.counter = new Counter(x + this.button.getId().getWidth(), y, image, false, count, scale);
	}

	@Override
	public void render(DPDTRenderer d) {
		this.button.render(d);
		this.counter.render(d);
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		this.button.setX(x);
		this.counter.setX(x + this.button.getId().getWidth());
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		this.button.setY(y);
		this.counter.setY(y);
	}

	@Override
	public void update() {
		this.button.update();
	}
}

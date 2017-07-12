package gui;

import display.DPDTRenderer;
import display.Image;

public class CounterButton extends GuiComponent {

	private MenuButton button;
	private Counter counter;

	public CounterButton(float x, float y, TypeButton type, Image buttonImage, Image counterImage, int count,
			float scale, ButtonAction action) {
		super(x, y);
		button = new MenuButton(type, buttonImage, x, y, action);
		counter = new Counter(x + button.getId().getWidth(), y, counterImage, false, count, scale);
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

	@Override
	public void update() {
		button.update();
	}
}

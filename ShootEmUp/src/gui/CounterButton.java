package gui;

import display.Art;
import display.DPDTRenderer;

public class CounterButton extends MenuItem{
	private Button button;
	private Counter counter;
	
	public CounterButton(float x, float y, TypeButton type, int count){
		super(x, y);
		button = ButtonBuilder.buildButton(type, x, y);
		counter = new Counter(x + button.getId().getWidth(), y, Art.numbers, false, count, 0.5f);
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
}

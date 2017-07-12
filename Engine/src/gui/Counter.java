package gui;

import display.ImageProcessor;
import display.DPDTRenderer;
import display.Image;
import math.Vector2;

public class Counter extends Icon {

	private int count;
	private Vector2 numberSize;
	private Vector2 fullSize;
	private int width;
	private final int FIRST_GAP = 5;
	private final int NUMBER_GAP = 25;
	private float scale;

	public Counter(float x, float y, Image i, boolean animating, int count, float scale) {
		super(x, y, i, animating, scale);
		this.scale = scale;
		this.count = count;
		width = (int) ((i.getWidth() / i.getFWidth()) * scale);
		numberSize = new Vector2((i.getHeight() / i.getFHeight()) * scale,
				(i.getHeight() / i.getFHeight()) * scale);
		fullSize = new Vector2(width + numberSize.x(), numberSize.y());
	}

	public Vector2 getFullSize() {
		return fullSize;
	}

	@Override
	public void render(DPDTRenderer r) {
		super.render(r);

		Vector2 maxTex = new Vector2(10, 1);

		if (count < 10) {
			ImageProcessor.stat.draw(ImageProcessor.getImage("Numbers"),
					new Vector2(x + width + (FIRST_GAP * scale), y), numberSize, 0.0f,
					new Vector2(count, 1), maxTex);
		} else {
			ImageProcessor.stat.draw(ImageProcessor.getImage("Numbers"),
					new Vector2(x + width + (FIRST_GAP * scale), y), numberSize, 0.0f,
					new Vector2(count / 10, 1), maxTex);
			ImageProcessor.stat.draw(ImageProcessor.getImage("Numbers"),
					new Vector2(x + width + (NUMBER_GAP * scale), y), numberSize, 0.0f,
					new Vector2(count % 10, 1), maxTex);
		}
	}

	public void update(int count) {
		super.update();
		this.count = count;
	}
}

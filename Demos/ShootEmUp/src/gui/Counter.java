package gui;

import org.joml.Vector2f;

import display.ArtLoader;
import display.DPDTRenderer;
import display.Image;
import display.ImageProcessor;

public class Counter extends Icon {

	private int count;
	private Vector2f numberSize;
	private Vector2f fullSize;
	private int numberWidth;
	private static final int FIRST_GAP = 5;
	private static final int NUMBER_GAP = 25;
	private float scale;

	public Counter(float x, float y, Image i, boolean animating, int count, float scale) {
		super(x, y, i, animating, scale);
		this.scale = scale;
		this.count = count;
		numberWidth = (int) ((i.getWidth() / i.getFWidth()) * scale);
		numberSize = new Vector2f((i.getHeight() / i.getFHeight()) * scale, (i.getHeight() / i.getFHeight()) * scale);
		fullSize = new Vector2f(numberWidth + numberSize.x(), numberSize.y());
	}

	public Counter(Image i, int count) {
		this(0, 0, i, false, count, 1.0f);
	}

	public Vector2f getFullSize() {
		return fullSize;
	}

	@Override
	public void render(DPDTRenderer r) {
		super.render(r);

		Vector2f maxTex = new Vector2f(10, 1);

		if (count < 10) {
			ImageProcessor.stat.draw(ArtLoader.getImage("Numbers"),
					new Vector2f(x + numberWidth + (FIRST_GAP * scale), y), numberSize, 0.0f, new Vector2f(count, 1),
					maxTex);
		} else {
			ImageProcessor.stat.draw(ArtLoader.getImage("Numbers"),
					new Vector2f(x + numberWidth + (FIRST_GAP * scale), y), numberSize, 0.0f,
					new Vector2f(count / 10, 1), maxTex);
			ImageProcessor.stat.draw(ArtLoader.getImage("Numbers"),
					new Vector2f(x + numberWidth + (NUMBER_GAP * scale), y), numberSize, 0.0f,
					new Vector2f(count % 10, 1), maxTex);
		}
	}

	public void update(int count) {
		super.update();
		this.count = count;
	}
}

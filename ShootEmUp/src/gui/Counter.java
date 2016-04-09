package gui;

import display.Art;
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
		this.width = (int) ((i.getWidth() / i.getFWidth()) * scale);
		this.numberSize = new Vector2((i.getHeight() / i.getFHeight()) * scale,
				(i.getHeight() / i.getFHeight()) * scale);
		this.fullSize = new Vector2(this.width + this.numberSize.x(), this.numberSize.y());
	}

	public Vector2 getFullSize() {
		return this.fullSize;
	}

	@Override
	public void render(DPDTRenderer r) {
		super.render(r);

		Vector2 maxTex = new Vector2(10, 1);

		if (this.count < 10) {
			Art.stat.draw(Art.getImage("Numbers"),
					new Vector2(this.x + this.width + (this.FIRST_GAP * this.scale), this.y), this.numberSize, 0.0f,
					new Vector2(this.count, 1), maxTex);
		} else {
			Art.stat.draw(Art.getImage("Numbers"),
					new Vector2(this.x + this.width + (this.FIRST_GAP * this.scale), this.y), this.numberSize, 0.0f,
					new Vector2((int) Math.floor(this.count / 10), 1), maxTex);
			Art.stat.draw(Art.getImage("Numbers"),
					new Vector2(this.x + this.width + (this.NUMBER_GAP * this.scale), this.y), this.numberSize, 0.0f,
					new Vector2(this.count % 10, 1), maxTex);
		}
	}

	public void update(int count) {
		super.update();
		this.count = count;
	}
}

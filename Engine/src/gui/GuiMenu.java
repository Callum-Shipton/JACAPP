package gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import display.Display;
import display.Image;
import display.ImageProcessor;
import loop.Loop;

public abstract class GuiMenu {

	protected List<GuiComponent> menuItems = new ArrayList<>();
	protected int buttonPointer = 0;
	protected Image menuImage;
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Display display;
	private boolean fullscreen;

	public GuiMenu(Image menuImage, Boolean fullscreen) {
		this.menuImage = menuImage;
		display = Loop.getDisplay();
		if (fullscreen) {
			x = 0;
			y = 0;
		} else {
			x = (display.getWidth() / 2) - (menuImage.getWidth() / 2);
			y = (display.getHeight() / 2) - (menuImage.getHeight() / 2);
		}
		w = menuImage.getWidth();
		h = menuImage.getHeight();
		this.fullscreen = fullscreen;
	}

	public void render() {
		if (fullscreen) {
			ImageProcessor.stat.draw(menuImage, new Vector2f(x, y),
					new Vector2f(display.getWidth(), display.getHeight()), 0, new Vector2f(0, 0), new Vector2f(1, 1));
		} else {
			ImageProcessor.stat.draw(menuImage, new Vector2f(x, y),
					new Vector2f(menuImage.getWidth(), menuImage.getHeight()), 0, new Vector2f(0, 0),
					new Vector2f(1, 1));
		}
		for (GuiComponent menuItem : menuItems) {
			menuItem.render(ImageProcessor.stat);
		}
	}

	public void update() {
		for (GuiComponent menuItem : menuItems) {
			menuItem.update();
		}
	}

	public abstract void resetMenu();

}

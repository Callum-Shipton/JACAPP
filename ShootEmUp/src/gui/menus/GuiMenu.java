package gui.menus;

import java.util.ArrayList;
import java.util.List;

import display.Art;
import display.Display;
import display.Image;
import gui.GuiComponent;
import gui.MenuButton;
import main.Loop;
import math.Vector2;

public abstract class GuiMenu {

	protected List<GuiComponent> menuItems = new ArrayList<>();
	protected int buttonPointer = 0;
	protected Image menuImage;
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Display display;

	public GuiMenu(Image menuImage) {
		this.menuImage = menuImage;
		this.x = 0;
		this.y = 0;
		this.w = menuImage.getWidth();
		this.h = menuImage.getHeight();
		this.display = Loop.getDisplay();
	}

	public void buttonPressed(MenuButton button) {

	}

	public void render() {
		Art.stat.draw(this.menuImage, new Vector2(this.x, this.y),
				new Vector2(this.display.getWidth(), this.display.getHeight()), 0, new Vector2(0, 0));
		for (GuiComponent menuItem : this.menuItems) {
			menuItem.render(Art.stat);
		}
	}

	public void update() {
		for (GuiComponent menuItem : this.menuItems) {
			menuItem.update();
		}
	}

}

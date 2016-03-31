package gui.menus;

import java.util.ArrayList;

import display.Art;
import display.Image;
import gui.Button;
import gui.GuiComponent;
import main.ShootEmUp;
import math.Vector2;

public abstract class GuiMenu {

	protected ArrayList<GuiComponent> menuItems = new ArrayList<GuiComponent>();
	protected int buttonPointer = 0;
	protected Image menuImage;
	protected int x;
	protected int y;
	protected int w;
	protected int h;

	public GuiMenu(Image menuImage) {
		this.menuImage = menuImage;
		x = 0;
		y = 0;
		w = menuImage.getWidth();
		h = menuImage.getHeight();
	}

	public void render() {
		Art.stat.draw(menuImage, new Vector2(x, y), new Vector2(ShootEmUp.width, ShootEmUp.height), 0,
				new Vector2(0, 0));
		for (GuiComponent menuItem : menuItems) {
			menuItem.render(Art.stat);
		}
	}

	public void update() {
		for (GuiComponent menuItem : menuItems) {
			menuItem.update();
		}
		/*
		 * if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1){
		 * Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
		 * ButtonHandler.selectButton(buttons.get(buttonPointer).getType()); }
		 * else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
		 * Keyboard.setKey(GLFW.GLFW_KEY_DOWN);
		 * buttons.get(buttonPointer).setHovered(false); buttonPointer++; if
		 * (buttonPointer >= buttons.size()) { buttonPointer = 0; }
		 * buttons.get(buttonPointer).setHovered(true); }else if
		 * (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
		 * Keyboard.setKey(GLFW.GLFW_KEY_UP);
		 * buttons.get(buttonPointer).setHovered(false); buttonPointer--; if
		 * (buttonPointer < 0) { buttonPointer = buttons.size() - 1; }
		 * buttons.get(buttonPointer).setHovered(true); }
		 */
	}

	public void buttonPressed(Button button) {

	}

	protected void popMenu() {
		ShootEmUp.menuStack.pop();
	}

	/*
	 * public void reset (int oldWidth, int oldHeight, int newWidth, int
	 * newHeight){ for(Button b : buttons){ b.reset(oldWidth, oldHeight,
	 * newWidth, newHeight); } }
	 */

}

package gui;

import java.util.Stack;

import display.Art;
import gui.menus.GuiMenu;
import gui.menus.InventoryMenu;

public class MenuSystem {
	private boolean mainMenu = true;
	private Stack<GuiMenu> menuStack = new Stack<GuiMenu>();

	public MenuSystem() {

	}

	public void addMenu(GuiMenu menu) {
		this.menuStack.add(menu);
	}

	public void clearMenus() {
		while (!this.menuStack.isEmpty()) {
			this.menuStack.pop();
		}
	}

	public boolean isMainMenu() {
		return this.mainMenu;
	}

	public void pause() {
		if (this.menuStack.isEmpty()) {
			addMenu(new InventoryMenu(Art.getImage("InventoryScreen")));
		} else {
			clearMenus();
		}
	}

	public void popMenu() {
		this.menuStack.pop();
	}

	public void render() {
		if (!this.menuStack.isEmpty()) {
			this.menuStack.peek().render();
		}
	}

	public void setMainMenu(boolean mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void update() {
		if (!this.menuStack.isEmpty()) {
			this.menuStack.peek().update();
		}
	}
}
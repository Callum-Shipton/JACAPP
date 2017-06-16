package gui;

import java.util.Deque;
import java.util.LinkedList;

import display.Art;
import gui.menus.InventoryMenu;

public class MenuSystem {
	private boolean mainMenu = true;
	private Deque<GuiMenu> menuStack = new LinkedList<>();

	public MenuSystem() {

	}

	public void addMenu(GuiMenu menu) {
		this.menuStack.addFirst(menu);
	}

	public void clearMenus() {
		while (!this.menuStack.isEmpty()) {
			this.menuStack.removeFirst();
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
		this.menuStack.removeFirst();
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
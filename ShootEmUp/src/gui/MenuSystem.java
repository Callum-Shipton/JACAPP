package gui;

import java.util.Deque;
import java.util.LinkedList;

public class MenuSystem {
	private boolean mainMenu = true;
	private Deque<GuiMenu> menuStack = new LinkedList<>();

	public void addMenu(GuiMenu menu) {
		menuStack.addFirst(menu);
	}

	public void clearMenus() {
		menuStack.clear();
	}

	public boolean isMainMenu() {
		return mainMenu;
	}

	public void popMenu() {
		menuStack.removeFirst();
	}

	public void render() {
		if (!menuStack.isEmpty()) {
			menuStack.peek().render();
		}
	}

	public void setMainMenu(boolean mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void update() {
		if (!menuStack.isEmpty()) {
			menuStack.peek().update();
		}
	}

	public void resetMenus() {
		for (GuiMenu menu : menuStack) {
			menu.resetMenu();
		}
	}
}
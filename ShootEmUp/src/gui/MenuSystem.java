package gui;

import java.util.Stack;

import display.Art;
import gui.menus.GuiMenu;
import gui.menus.InventoryMenu;

public class MenuSystem {
	private boolean mainMenu = true;
	private Stack<GuiMenu> menuStack = new Stack<GuiMenu>();

	public MenuSystem(){
		
	}
	
	public void update() {
		if (!menuStack.isEmpty()) {
			menuStack.peek().update();
		}
	}
	
	public void pause(){
		if (menuStack.isEmpty()) {
			addMenu(new InventoryMenu(Art.getImage("InventoryScreen")));
		} else {
			clearMenus();
		}
	}
	
	public void render(){
		if (!menuStack.isEmpty()) {
			menuStack.peek().render();
		}
	}
	
	public void clearMenus() {
		while (!menuStack.isEmpty()) {
			menuStack.pop();
		}
	}

	public void addMenu(GuiMenu menu) {
		menuStack.add(menu);
	}
	
	public void popMenu(){
		menuStack.pop();
	}

	public boolean isMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(boolean mainMenu) {
		this.mainMenu = mainMenu;
	}
}
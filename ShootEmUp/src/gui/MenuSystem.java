package gui;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

import java.util.Stack;

import display.Art;
import gui.menus.GuiMenu;
import gui.menus.InventoryMenu;
import input.Keyboard;
import main.ShootEmUp;

public class MenuSystem {
	private boolean mainMenu = true;
	private Stack<GuiMenu> menuStack = new Stack<GuiMenu>();

	public MenuSystem(){
		
	}
	
	public void update() {
		if (!mainMenu) {
			if (Keyboard.getKey(GLFW_KEY_P) == 1) {
				ShootEmUp.setPaused(!ShootEmUp.isPaused());
				Keyboard.setKey(GLFW_KEY_P);
				if (ShootEmUp.isPaused()) {
					addMenu(new InventoryMenu(Art.getImage("InventoryScreen")));
				} else {
					clearMenus();
				}
			}
		}
		if (!menuStack.isEmpty()) {
			menuStack.peek().update();
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
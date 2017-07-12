package gui.buttons;

import display.ImageProcessor;
import gui.ButtonAction;
import gui.menus.InventoryMenu;
import main.ShootEmUp;

public class InventoryButton implements ButtonAction {

	@Override
	public void click() {
		ShootEmUp.getMenuSystem().addMenu(new InventoryMenu(ImageProcessor.getImage("InventoryScreen")));
	}
}

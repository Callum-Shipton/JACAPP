package gui.buttons;

import gui.ButtonAction;
import gui.GuiMenu;
import main.ShootEmUp;

public class OpenMenuButton implements ButtonAction {

	private GuiMenu menu;

	public OpenMenuButton(GuiMenu menu) {
		this.menu = menu;
	}

	@Override
	public void click() {
		ShootEmUp.getMenuSystem().addMenu(menu);
	}
}

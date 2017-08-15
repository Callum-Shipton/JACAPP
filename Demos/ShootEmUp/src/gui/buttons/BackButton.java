package gui.buttons;

import gui.ButtonAction;
import main.ShootEmUp;

public class BackButton implements ButtonAction {
	@Override
	public void click() {
		ShootEmUp.getMenuSystem().popMenu();
	}
}

package gui.buttons;

import gui.ButtonAction;
import main.ShootEmUp;

public class ResumeButton implements ButtonAction {
	@Override
	public void click() {
		ShootEmUp.getMenuSystem().clearMenus();
		ShootEmUp.setPaused(false);
	}
}

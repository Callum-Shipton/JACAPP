package gui.buttons;

import gui.ButtonAction;
import main.ShootEmUp;

public class MuteButton implements ButtonAction {
	@Override
	public void click() {
		ShootEmUp.getMusicPlayer().pause();
	}
}

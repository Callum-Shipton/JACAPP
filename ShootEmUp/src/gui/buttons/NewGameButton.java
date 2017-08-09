package gui.buttons;

import gui.ButtonAction;
import main.ShootEmUp;

public class NewGameButton implements ButtonAction {

	@Override
	public void click() {
		ShootEmUp.getGame().newGame();
	}
}

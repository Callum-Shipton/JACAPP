package gui.buttons;

import gui.ButtonAction;
import main.ShootEmUp;
import save.Save;

public class SaveButton implements ButtonAction {

	@Override
	public void click() {
		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new Save());
		}
		// TODO fix saves
		ShootEmUp.getSave().saveCharacter("well fuck");
		ShootEmUp.getSave().saveToSystem(1);
		ShootEmUp.setSave(null);
	}

}

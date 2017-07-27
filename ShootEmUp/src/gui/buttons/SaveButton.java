package gui.buttons;

import components.TypeComponent;
import gui.ButtonAction;
import main.ShootEmUp;
import save.ShootEmUpSave;

public class SaveButton implements ButtonAction {

	@Override
	public void click() {
		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new ShootEmUpSave());
		}
		// TODO fix saves
		ShootEmUp.getSave().saveCharacter("well fuck",
				ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK));
		ShootEmUp.getSave().saveToSystem(1);
		ShootEmUp.setSave(null);
	}

}

package gui.buttons;

import components.attack.TypeAttack;
import display.ImageProcessor;
import gui.ButtonAction;
import gui.menus.CharacterSelectMenu;
import logging.Logger;
import main.ShootEmUp;
import save.Save;

public class LoadGameButton implements ButtonAction {
	@Override
	public void click() {
		Save save = ShootEmUp.getSave();

		int warriorLevel = 0;
		int archerLevel = 0;
		int mageLevel = 0;
		int battleMageLevel = 0;
		int rogueLevel = 0;

		if (save == null) {
			save = new Save();
			ShootEmUp.setSave(save);
		}
		try {
			save.load(1);
			if (save.getCharacter(TypeAttack.WARRIOR) != null) {
				warriorLevel = save.getCharacter(TypeAttack.WARRIOR).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.ARCHER) != null) {
				archerLevel = save.getCharacter(TypeAttack.ARCHER).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.MAGE) != null) {
				mageLevel = save.getCharacter(TypeAttack.MAGE).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.BATTLE_MAGE) != null) {
				battleMageLevel = save.getCharacter(TypeAttack.BATTLE_MAGE).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.ROGUE) != null) {
				rogueLevel = save.getCharacter(TypeAttack.ROGUE).getPlayerLevel();
			}
		} catch (Exception e) {
			Logger.error(e);
		}

		ShootEmUp.getMenuSystem().addMenu(new CharacterSelectMenu(ImageProcessor.getImage("MainMenuScreen"),
				warriorLevel, archerLevel, mageLevel, battleMageLevel, rogueLevel));
	}
}

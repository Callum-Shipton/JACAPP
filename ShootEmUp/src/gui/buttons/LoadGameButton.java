package gui.buttons;

import display.ImageProcessor;
import gui.ButtonAction;
import gui.menus.CharacterSelectMenu;
import logging.Logger;
import main.ShootEmUp;
import save.ShootEmUpSave;

public class LoadGameButton implements ButtonAction {
	@Override
	public void click() {
		ShootEmUpSave save = ShootEmUp.getSave();

		int warriorLevel = 0;
		int archerLevel = 0;
		int mageLevel = 0;
		int battleMageLevel = 0;
		int rogueLevel = 0;

		if (save == null) {
			save = new ShootEmUpSave();
			ShootEmUp.setSave(save);
		}
		try {
			save.load(1);
			if (save.getCharacter("Warrior") != null) {
				warriorLevel = save.getCharacter("Warrior").getPlayerLevel();
			}
			if (save.getCharacter("Archer") != null) {
				archerLevel = save.getCharacter("Archer").getPlayerLevel();
			}
			if (save.getCharacter("Mage") != null) {
				mageLevel = save.getCharacter("Mage").getPlayerLevel();
			}
			if (save.getCharacter("BattleMage") != null) {
				battleMageLevel = save.getCharacter("BattleMage").getPlayerLevel();
			}
			if (save.getCharacter("Rogue") != null) {
				rogueLevel = save.getCharacter("Rogue").getPlayerLevel();
			}
		} catch (Exception e) {
			Logger.error(e);
		}

		ShootEmUp.getMenuSystem().addMenu(new CharacterSelectMenu(ImageProcessor.getImage("MainMenuScreen"),
				warriorLevel, archerLevel, mageLevel, battleMageLevel, rogueLevel));
	}
}

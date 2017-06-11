package gui.menus;

import components.attack.TypeAttack;
import display.Art;
import display.Image;
import gui.CounterButton;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;
import main.ShootEmUp;

public class CharacterSelectMenu extends GuiMenu {

	public CharacterSelectMenu(Image menuImage) {
		super(menuImage);

		int warriorLevel = 0;
		int archerLevel = 0;
		int mageLevel = 0;
		int battleMageLevel = 0;
		int rogueLevel = 0;

		if (ShootEmUp.getSave() != null) {
			if (ShootEmUp.getSave().getCharacter(TypeAttack.WARRIOR) != null) {
				warriorLevel = ShootEmUp.getSave().getCharacter(TypeAttack.WARRIOR).getPlayerLevel();
			}
			if (ShootEmUp.getSave().getCharacter(TypeAttack.ARCHER) != null) {
				archerLevel = ShootEmUp.getSave().getCharacter(TypeAttack.ARCHER).getPlayerLevel();
			}
			if (ShootEmUp.getSave().getCharacter(TypeAttack.MAGE) != null) {
				mageLevel = ShootEmUp.getSave().getCharacter(TypeAttack.MAGE).getPlayerLevel();
			}
			if (ShootEmUp.getSave().getCharacter(TypeAttack.BATTLE_MAGE) != null) {
				battleMageLevel = ShootEmUp.getSave().getCharacter(TypeAttack.BATTLE_MAGE).getPlayerLevel();
			}
			if (ShootEmUp.getSave().getCharacter(TypeAttack.ROGUE) != null) {
				rogueLevel = ShootEmUp.getSave().getCharacter(TypeAttack.ROGUE).getPlayerLevel();
			}
		}

		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (Art.getImage("WarriorButton").getWidth() / 2), 150,
				Art.getImage("WarriorButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.WARRIOR, Art.getImage("WarriorButton"),
				Art.getImage("LevelIcon"), warriorLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ARCHER, Art.getImage("WarriorButton"),
				Art.getImage("LevelIcon"), archerLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MAGE, Art.getImage("MageButton"),
				Art.getImage("LevelIcon"), mageLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.BATTLE_MAGE, Art.getImage("BattleMageButton"),
				Art.getImage("LevelIcon"), battleMageLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ROGUE, Art.getImage("RogueButton"),
				Art.getImage("LevelIcon"), rogueLevel, 0.5f));
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, Art.getImage("BackButton"), x, y));
		this.menuItems.add(buttonList);
	}
}

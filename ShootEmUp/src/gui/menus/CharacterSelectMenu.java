package gui.menus;

import display.Art;
import display.Image;
import gui.CounterButton;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public class CharacterSelectMenu extends GuiMenu {

	public CharacterSelectMenu(Image menuImage) {
		
		this(menuImage, 0, 0, 0, 0, 0);
		
	}
	
	public CharacterSelectMenu(Image menuImage, int warriorLevel, int archerLevel, int mageLevel, int battleMageLevel, int rogueLevel) {
		super(menuImage);
		
		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (Art.getImage("WarriorButton").getWidth() / 2), 150,
				Art.getImage("WarriorButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.WARRIOR, Art.getImage("WarriorButton"),
				Art.getImage("LevelIcon"), warriorLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ARCHER, Art.getImage("ArcherButton"),
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

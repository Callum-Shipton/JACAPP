package gui.menus;

import display.ImageProcessor;
import display.Image;
import gui.CounterButton;
import gui.GuiMenu;
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
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("WarriorButton").getWidth() / 2), 150,
				ImageProcessor.getImage("WarriorButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.WARRIOR, ImageProcessor.getImage("WarriorButton"),
				ImageProcessor.getImage("LevelIcon"), warriorLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ARCHER, ImageProcessor.getImage("ArcherButton"),
				ImageProcessor.getImage("LevelIcon"), archerLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MAGE, ImageProcessor.getImage("MageButton"),
				ImageProcessor.getImage("LevelIcon"), mageLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.BATTLE_MAGE, ImageProcessor.getImage("BattleMageButton"),
				ImageProcessor.getImage("LevelIcon"), battleMageLevel, 0.5f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ROGUE, ImageProcessor.getImage("RogueButton"),
				ImageProcessor.getImage("LevelIcon"), rogueLevel, 0.5f));
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"), x, y));
		this.menuItems.add(buttonList);
	}
}

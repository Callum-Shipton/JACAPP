package gui.menus;

import components.attack.TypeAttack;
import display.Image;
import display.ImageProcessor;
import gui.CounterButton;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.BackButton;
import gui.buttons.SelectCharacterButton;

public class CharacterSelectMenu extends GuiMenu {

	private int warriorLevel = 0;
	private int archerLevel = 0;
	private int mageLevel = 0;
	private int battleMageLevel = 0;
	private int rogueLevel = 0;

	public CharacterSelectMenu(Image menuImage) {
		super(menuImage, true);
		resetMenu();
	}

	public CharacterSelectMenu(Image menuImage, int warriorLevel, int archerLevel, int mageLevel, int battleMageLevel,
			int rogueLevel) {
		super(menuImage, true);
		this.warriorLevel = warriorLevel;
		this.archerLevel = archerLevel;
		this.mageLevel = mageLevel;
		this.battleMageLevel = battleMageLevel;
		this.rogueLevel = rogueLevel;
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage("WarriorButton").getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage("WarriorButton").getHeight() / 2, 20);
		buttonList.addMenuItem(
				new CounterButton(0, 0, ImageProcessor.getImage("WarriorButton"), ImageProcessor.getImage("LevelIcon"),
						warriorLevel, 0.5f, new SelectCharacterButton(TypeAttack.WARRIOR)));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("ArcherButton"),
				ImageProcessor.getImage("LevelIcon"), archerLevel, 0.5f, new SelectCharacterButton(TypeAttack.ARCHER)));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("MageButton"),
				ImageProcessor.getImage("LevelIcon"), mageLevel, 0.5f, new SelectCharacterButton(TypeAttack.MAGE)));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("BattleMageButton"),
				ImageProcessor.getImage("LevelIcon"), battleMageLevel, 0.5f,
				new SelectCharacterButton(TypeAttack.BATTLE_MAGE)));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("RogueButton"),
				ImageProcessor.getImage("LevelIcon"), rogueLevel, 0.5f, new SelectCharacterButton(TypeAttack.ROGUE)));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("BackButton"), x, y, new BackButton()));
		menuItems.add(buttonList);
	}
}

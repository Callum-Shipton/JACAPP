package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.VerticalLayout;
import gui.TypeButton;
import main.ShootEmUp;

public class MainMenu extends GuiMenu {

	public MainMenu(Image menuImage) {
		super(menuImage);
		VerticalLayout buttonList = new VerticalLayout((ShootEmUp.width / 2) - (Art.getImage("NewGameButton").getWidth() / 2),
				150, Art.getImage("NewGameButton").getHeight() / 2, 20);
		ShootEmUp.currentLevel = null;
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.NEW_GAME, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LOAD_GAME, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.OPTIONS, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.EXIT, 0, 0));
		menuItems.add(buttonList);
	}
}

package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;
import main.ShootEmUp;

public class MainMenu extends GuiMenu {

	public MainMenu(Image menuImage) {
		super(menuImage);
		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (Art.getImage("NewGameButton").getWidth() / 2), 150,
				Art.getImage("NewGameButton").getHeight() / 2, 20);
		ShootEmUp.setCurrentLevel(null);
		buttonList.addMenuItem(new MenuButton(TypeButton.NEW_GAME, Art.getImage("NewGameButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.LOAD_GAME, Art.getImage("LoadGameButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.OPTIONS, Art.getImage("OptionsButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.EXIT, Art.getImage("ExitButton"), 0, 0));
		this.menuItems.add(buttonList);
	}
}

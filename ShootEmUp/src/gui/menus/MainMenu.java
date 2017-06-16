package gui.menus;

import display.ImageProcessor;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public class MainMenu extends GuiMenu {

	public MainMenu(Image menuImage) {
		super(menuImage);
		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("NewGameButton").getWidth() / 2), 150,
				ImageProcessor.getImage("NewGameButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new MenuButton(TypeButton.NEW_GAME, ImageProcessor.getImage("NewGameButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.LOAD_GAME, ImageProcessor.getImage("LoadGameButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.OPTIONS, ImageProcessor.getImage("OptionsButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.EXIT, ImageProcessor.getImage("ExitButton"), 0, 0));
		this.menuItems.add(buttonList);
	}
}

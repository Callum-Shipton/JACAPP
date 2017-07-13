package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.ExitButton;
import gui.buttons.LoadGameButton;
import gui.buttons.OpenMenuButton;

public class MainMenu extends GuiMenu {

	public MainMenu(Image menuImage) {
		super(menuImage, true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage("NewGameButton").getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage("NewGameButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("NewGameButton"), 0, 0,
				new OpenMenuButton(new CharacterSelectMenu(ImageProcessor.getImage("MainMenuScreen")))));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("LoadGameButton"), 0, 0, new LoadGameButton()));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("OptionsButton"), 0, 0,
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("MainMenuScreen")))));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("ExitButton"), 0, 0, new ExitButton()));
		menuItems.add(buttonList);
	}
}

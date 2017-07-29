package gui.menus;

import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.ExitButton;
import gui.buttons.NewGameButton;
import gui.buttons.OpenMenuButton;

public class MainMenu extends GuiMenu {

	public MainMenu() {
		super(ImageProcessor.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String newGameButton = "NewGameButton";

		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage(newGameButton).getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage(newGameButton).getHeight() / 2, 20);
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage(newGameButton), 0, 0, new NewGameButton()));
		buttonList.addMenuItem(
				new MenuButton(ImageProcessor.getImage("LoadGameButton"), 0, 0, new OpenMenuButton(new LoadMenu())));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("OptionsButton"), 0, 0,
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("MainMenuScreen")))));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("ExitButton"), 0, 0, new ExitButton()));
		menuItems.add(buttonList);
	}
}

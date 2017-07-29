package gui.menus;

import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.BackButton;
import gui.buttons.LoadGameButton;

public class LoadMenu extends GuiMenu {

	public LoadMenu() {
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
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("Save1Button"), 0, 0, new LoadGameButton(1)));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("Save2Button"), 0, 0, new LoadGameButton(2)));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("Save3Button"), 0, 0, new LoadGameButton(3)));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("BackButton"), 0, 0, new BackButton()));
		menuItems.add(buttonList);
	}

}

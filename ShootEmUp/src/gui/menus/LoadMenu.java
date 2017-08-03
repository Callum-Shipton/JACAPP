package gui.menus;

import display.ImageProcessor;
import gui.GuiMenu;
import gui.Button;
import gui.buttons.BackButton;
import gui.buttons.LoadGameButton;
import gui.layouts.VerticalLayout;

public class LoadMenu extends GuiMenu {

	public LoadMenu() {
		super(ImageProcessor.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String newGameButton = "NewGameButton";

		clearMenu();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage(newGameButton).getWidth() / 2),
				display.getHeight() / 2, 20);
		buttonList.addMenuItem(new Button(ImageProcessor.getImage("Save1Button"), new LoadGameButton(1)));
		buttonList.addMenuItem(new Button(ImageProcessor.getImage("Save2Button"), new LoadGameButton(2)));
		buttonList.addMenuItem(new Button(ImageProcessor.getImage("Save3Button"), new LoadGameButton(3)));
		buttonList.addMenuItem(new Button(ImageProcessor.getImage("BackButton"), new BackButton()));
		addMenuItem(buttonList);
	}

}

package gui.menus;

import display.ImageProcessor;
import gui.Button;
import gui.GuiMenu;
import gui.buttons.ExitButton;
import gui.buttons.NewGameButton;
import gui.buttons.OpenMenuButton;
import gui.layouts.VerticalLayout;

public class MainMenu extends GuiMenu {

	public MainMenu() {
		super(ImageProcessor.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String newGameButtonId = "NewGameButton";
		clearMenu();

		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage(newGameButtonId).getWidth() / 2),
				display.getHeight() / 2, 20);
		Button newGameButton = new Button(ImageProcessor.getImage(newGameButtonId), new NewGameButton());
		Button loadGameButton = new Button(ImageProcessor.getImage("LoadGameButton"),
				new OpenMenuButton(new LoadMenu()));
		Button optionsButton = new Button(ImageProcessor.getImage("OptionsButton"),
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("MainMenuScreen"))));
		Button exitButton = new Button(ImageProcessor.getImage("ExitButton"), new ExitButton());

		buttonList.addMenuItem(newGameButton);
		// buttonList.addMenuItem(loadGameButton);
		buttonList.addMenuItem(optionsButton);
		buttonList.addMenuItem(exitButton);
		addMenuItem(buttonList);
	}
}

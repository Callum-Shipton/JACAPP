package gui.menus;

import display.ArtLoader;
import gui.Button;
import gui.GuiMenu;
import gui.buttons.ExitButton;
import gui.buttons.NewGameButton;
import gui.buttons.OpenMenuButton;
import gui.layouts.VerticalLayout;

public class MainMenu extends GuiMenu {

	public MainMenu() {
		super(ArtLoader.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String newGameButtonId = "NewGameButton";
		clearMenu();

		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ArtLoader.getImage(newGameButtonId).getWidth() / 2),
				display.getHeight() / 2, 20);
		Button newGameButton = new Button(ArtLoader.getImage(newGameButtonId), new NewGameButton());
		Button loadGameButton = new Button(ArtLoader.getImage("LoadGameButton"),
				new OpenMenuButton(new LoadMenu()));
		Button optionsButton = new Button(ArtLoader.getImage("OptionsButton"),
				new OpenMenuButton(new OptionsMenu(ArtLoader.getImage("MainMenuScreen"))));
		Button exitButton = new Button(ArtLoader.getImage("ExitButton"), new ExitButton());

		buttonList.addMenuItem(newGameButton);
		// buttonList.addMenuItem(loadGameButton);
		buttonList.addMenuItem(optionsButton);
		buttonList.addMenuItem(exitButton);
		addMenuItem(buttonList);
	}
}

package gui.menus;

import gui.GuiMenu;
import display.ArtLoader;
import gui.Button;
import gui.buttons.BackButton;
import gui.buttons.MuteButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu() {
		super(ArtLoader.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String muteButton = "MuteButton";

		clearMenu();
		addMenuItem(new Button(ArtLoader.getImage(muteButton),
				(display.getWidth() / 2) - (ArtLoader.getImage(muteButton).getWidth() / 2),
				(display.getHeight() / 2) - (ArtLoader.getImage(muteButton).getHeight() / 2), new MuteButton()));
		addMenuItem(new Button(ArtLoader.getImage("BackButton"),
				(display.getWidth() / 2) - (ArtLoader.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (ArtLoader.getImage("SoundButton").getHeight() / 2),
				new BackButton()));
	}
}

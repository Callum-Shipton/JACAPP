package gui.menus;

import display.Art;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.Button;
import gui.buttons.BackButton;
import gui.buttons.MuteButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu() {
		super(Art.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String muteButton = "MuteButton";

		clearMenu();
		addMenuItem(new Button(Art.getImage(muteButton),
				(display.getWidth() / 2) - (Art.getImage(muteButton).getWidth() / 2),
				(display.getHeight() / 2) - (Art.getImage(muteButton).getHeight() / 2), new MuteButton()));
		addMenuItem(new Button(Art.getImage("BackButton"),
				(display.getWidth() / 2) - (Art.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (Art.getImage("SoundButton").getHeight() / 2),
				new BackButton()));
	}
}

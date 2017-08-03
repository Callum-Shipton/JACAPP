package gui.menus;

import display.ImageProcessor;
import gui.GuiMenu;
import gui.Button;
import gui.buttons.BackButton;
import gui.buttons.MuteButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu() {
		super(ImageProcessor.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String muteButton = "MuteButton";

		clearMenu();
		addMenuItem(new Button(ImageProcessor.getImage(muteButton),
				(display.getWidth() / 2) - (ImageProcessor.getImage(muteButton).getWidth() / 2),
				(display.getHeight() / 2) - (ImageProcessor.getImage(muteButton).getHeight() / 2), new MuteButton()));
		addMenuItem(new Button(ImageProcessor.getImage("BackButton"),
				(display.getWidth() / 2) - (ImageProcessor.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (ImageProcessor.getImage("SoundButton").getHeight() / 2),
				new BackButton()));
	}
}

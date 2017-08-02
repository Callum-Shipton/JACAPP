package gui.menus;

import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
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

		menuItems.clear();
		menuItems.add(new MenuButton(ImageProcessor.getImage(muteButton),
				(display.getWidth() / 2) - (ImageProcessor.getImage(muteButton).getWidth() / 2),
				(display.getHeight() / 2) - (ImageProcessor.getImage(muteButton).getHeight() / 2), new MuteButton()));
		menuItems.add(new MenuButton(ImageProcessor.getImage("BackButton"),
				(display.getWidth() / 2) - (ImageProcessor.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (ImageProcessor.getImage("SoundButton").getHeight() / 2),
				new BackButton()));
	}
}

package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.buttons.BackButton;
import gui.buttons.MuteButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage, true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		menuItems.add(new MenuButton(ImageProcessor.getImage("MuteButton"),
				(display.getWidth() / 2) - (ImageProcessor.getImage("MuteButton").getWidth() / 2),
				(display.getHeight() / 2) - (ImageProcessor.getImage("MuteButton").getHeight() / 2), new MuteButton()));
		menuItems.add(new MenuButton(ImageProcessor.getImage("BackButton"),
				(display.getWidth() / 2) - (ImageProcessor.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (ImageProcessor.getImage("SoundButton").getHeight() / 2),
				new BackButton()));
	}
}

package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		menuItems.add(new MenuButton(TypeButton.MUTE, ImageProcessor.getImage("MuteButton"),
				(display.getWidth() / 2) - (ImageProcessor.getImage("MuteButton").getWidth() / 2),
				(display.getHeight() / 2) - (ImageProcessor.getImage("MuteButton").getHeight() / 2)));
		menuItems.add(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"),
				(display.getWidth() / 2) - (ImageProcessor.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (ImageProcessor.getImage("SoundButton").getHeight() / 2)));
	}
}

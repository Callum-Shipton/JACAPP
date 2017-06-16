package gui.menus;

import display.ImageProcessor;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(new MenuButton(TypeButton.MUTE, ImageProcessor.getImage("MuteButton"),
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("MuteButton").getWidth() / 2),
				(this.display.getHeight() / 2) - (ImageProcessor.getImage("MuteButton").getHeight() / 2)));
		this.menuItems.add(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"),
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("SoundButton").getWidth() / 2),
				(this.display.getHeight() / 2) + (ImageProcessor.getImage("SoundButton").getHeight() / 2)));
	}
}

package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(new MenuButton(TypeButton.MUTE, Art.getImage("MuteButton"),
				(this.display.getWidth() / 2) - (Art.getImage("MuteButton").getWidth() / 2),
				(this.display.getHeight() / 2) - (Art.getImage("MuteButton").getHeight() / 2)));
		this.menuItems.add(new MenuButton(TypeButton.BACK, Art.getImage("BackButton"),
				(this.display.getWidth() / 2) - (Art.getImage("SoundButton").getWidth() / 2),
				(this.display.getHeight() / 2) + (Art.getImage("SoundButton").getHeight() / 2)));
	}
}

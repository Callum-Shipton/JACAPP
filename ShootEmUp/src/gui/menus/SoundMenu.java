package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(ButtonBuilder.buildButton(TypeButton.MUTE,
				(this.display.getWidth() / 2) - (Art.getImage("MuteButton").getWidth() / 2),
				(this.display.getHeight() / 2) - (Art.getImage("MuteButton").getHeight() / 2)));
		this.menuItems.add(ButtonBuilder.buildButton(TypeButton.BACK,
				(this.display.getWidth() / 2) - (Art.getImage("SoundButton").getWidth() / 2),
				(this.display.getHeight() / 2) + (Art.getImage("SoundButton").getHeight() / 2)));
	}
}

package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage);
		menuItems.add(ButtonBuilder.buildButton(TypeButton.MUTE,
				(display.getWidth() / 2) - (Art.getImage("MuteButton").getWidth() / 2),
				(display.getHeight() / 2) - (Art.getImage("MuteButton").getHeight() / 2)));
		menuItems.add(ButtonBuilder.buildButton(TypeButton.BACK,
				(display.getWidth() / 2) - (Art.getImage("SoundButton").getWidth() / 2),
				(display.getHeight() / 2) + (Art.getImage("SoundButton").getHeight() / 2)));
	}
}

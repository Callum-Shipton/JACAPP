package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

public class SoundMenu extends GuiMenu {

	public SoundMenu(Image menuImage) {
		super(menuImage);
		menuItems.add(ButtonBuilder.buildButton(TypeButton.MUTE,
				(ShootEmUp.width / 2) - (Art.getImage("MuteButton").getWidth() / 2),
				(ShootEmUp.height / 2) - (Art.getImage("MuteButton").getHeight() / 2)));
		menuItems.add(ButtonBuilder.buildButton(TypeButton.BACK,
				(ShootEmUp.width / 2) - (Art.getImage("SoundButton").getWidth() / 2),
				(ShootEmUp.height / 2) + (Art.getImage("SoundButton").getHeight() / 2)));
	}
}

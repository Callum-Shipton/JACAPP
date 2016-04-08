package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;

public class LoadMenu extends GuiMenu {

	public LoadMenu(Image menuImage) {
		super(menuImage);
		menuItems.add(ButtonBuilder.buildButton(TypeButton.BACK,
				(display.getWidth() / 2) - (Art.getImage("BackButton").getWidth() / 2),
				(display.getHeight() / 2) - (Art.getImage("BackButton").getHeight() / 2)));
	}
}

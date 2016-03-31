package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

public class ControlsMenu extends GuiMenu {

	public ControlsMenu(Image menuImage) {
		super(menuImage);
		menuItems.add(ButtonBuilder.buildButton(TypeButton.BACK,
				(ShootEmUp.width / 2) - (Art.getImage("BackButton").getWidth() / 2),
				(ShootEmUp.height / 2) - (Art.getImage("BackButton").getHeight() / 2)));
	}
}

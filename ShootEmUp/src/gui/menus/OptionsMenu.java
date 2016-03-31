package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage);

		ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.getImage("ControlsButton").getWidth() / 2),
				150, Art.getImage("ControlsButton").getHeight() / 2, 20);

		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.CONTROLS, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.SOUND, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, 0, 0));

		menuItems.add(buttonList);
	}
}

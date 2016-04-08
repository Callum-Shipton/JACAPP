package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.VerticalLayout;
import gui.TypeButton;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout buttonList = new VerticalLayout((display.getWidth() / 2) - (Art.getImage("ControlsButton").getWidth() / 2),
				150, Art.getImage("ControlsButton").getHeight() / 2, 20);

		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.CONTROLS, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.SOUND, 0, 0));
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, 0, 0));

		menuItems.add(buttonList);
	}
}

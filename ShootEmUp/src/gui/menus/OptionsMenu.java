package gui.menus;

import display.Art;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (Art.getImage("ControlsButton").getWidth() / 2), 150,
				Art.getImage("ControlsButton").getHeight() / 2, 20);

		buttonList.addMenuItem(new MenuButton(TypeButton.CONTROLS, Art.getImage("ControlsButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.SOUND, Art.getImage("SoundButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, Art.getImage("BackButton"), 0, 0));

		this.menuItems.add(buttonList);
	}
}

package gui.menus;

import display.Art;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;

public class ControlsMenu extends GuiMenu {

	public ControlsMenu(Image menuImage) {
		super(menuImage);
		Image button = Art.getImage("BackButton");
		this.menuItems.add(new MenuButton(TypeButton.BACK, button, (this.display.getWidth() / 2) - (button.getWidth() / 2),
				(this.display.getHeight() / 2) - (button.getHeight() / 2)));
	}
}

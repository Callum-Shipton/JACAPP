package gui.menus;

import display.Art;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;

public class LoadMenu extends GuiMenu {

	public LoadMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(new MenuButton(TypeButton.BACK, Art.getImage("BackButton"),
				(this.display.getWidth() / 2) - (Art.getImage("BackButton").getWidth() / 2),
				(this.display.getHeight() / 2) - (Art.getImage("BackButton").getHeight() / 2)));
	}
}

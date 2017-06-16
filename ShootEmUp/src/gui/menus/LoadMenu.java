package gui.menus;

import display.ImageProcessor;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;

public class LoadMenu extends GuiMenu {

	public LoadMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"),
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("BackButton").getWidth() / 2),
				(this.display.getHeight() / 2) - (ImageProcessor.getImage("BackButton").getHeight() / 2)));
	}
}

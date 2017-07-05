package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;

public class ControlsMenu extends GuiMenu {

	public ControlsMenu(Image menuImage) {
		super(menuImage);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		Image button = ImageProcessor.getImage("BackButton");
		menuItems.add(new MenuButton(TypeButton.BACK, button, (display.getWidth() / 2) - (button.getWidth() / 2),
				(display.getHeight() / 2) - (button.getHeight() / 2)));
	}
}

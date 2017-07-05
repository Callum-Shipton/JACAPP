package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage, true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage("ControlsButton").getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage("ControlsButton").getHeight() / 2, 20);

		buttonList.addMenuItem(new MenuButton(TypeButton.CONTROLS, ImageProcessor.getImage("ControlsButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.SOUND, ImageProcessor.getImage("SoundButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"), 0, 0));

		menuItems.add(buttonList);
	}
}

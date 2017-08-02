package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.BackButton;
import gui.buttons.OpenMenuButton;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage, true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String controlButton = "ControlsButton";

		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage(controlButton).getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage(controlButton).getHeight() / 2, 20);

		buttonList.addMenuItem(
				new MenuButton(ImageProcessor.getImage(controlButton), 0, 0, new OpenMenuButton(new ControlsMenu())));
		buttonList.addMenuItem(
				new MenuButton(ImageProcessor.getImage("SoundButton"), 0, 0, new OpenMenuButton(new SoundMenu())));
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("BackButton"), 0, 0, new BackButton()));

		menuItems.add(buttonList);
	}
}

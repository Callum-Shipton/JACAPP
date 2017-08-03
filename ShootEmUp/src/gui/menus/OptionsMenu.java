package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.Button;
import gui.buttons.BackButton;
import gui.buttons.OpenMenuButton;
import gui.layouts.VerticalLayout;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage, true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String controlButton = "ControlsButton";

		clearMenu();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage(controlButton).getWidth() / 2),
				display.getHeight() / 2, 20);

		buttonList.addMenuItem(
				new Button(ImageProcessor.getImage(controlButton), new OpenMenuButton(new ControlsMenu())));
		buttonList.addMenuItem(
				new Button(ImageProcessor.getImage("SoundButton"), new OpenMenuButton(new SoundMenu())));
		buttonList.addMenuItem(new Button(ImageProcessor.getImage("BackButton"), new BackButton()));

		addMenuItem(buttonList);
	}
}

package gui.menus;

import display.ArtLoader;
import display.Image;
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
				(display.getWidth() / 2) - (ArtLoader.getImage(controlButton).getWidth() / 2),
				display.getHeight() / 2, 20);

		buttonList.addMenuItem(
				new Button(ArtLoader.getImage(controlButton), new OpenMenuButton(new ControlsMenu())));
		buttonList.addMenuItem(
				new Button(ArtLoader.getImage("SoundButton"), new OpenMenuButton(new SoundMenu())));
		buttonList.addMenuItem(new Button(ArtLoader.getImage("BackButton"), new BackButton()));

		addMenuItem(buttonList);
	}
}

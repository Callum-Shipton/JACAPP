package gui.menus;

import display.ImageProcessor;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public class OptionsMenu extends GuiMenu {

	public OptionsMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("ControlsButton").getWidth() / 2), 150,
				ImageProcessor.getImage("ControlsButton").getHeight() / 2, 20);

		buttonList.addMenuItem(new MenuButton(TypeButton.CONTROLS, ImageProcessor.getImage("ControlsButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.SOUND, ImageProcessor.getImage("SoundButton"), 0, 0));
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"), 0, 0));

		this.menuItems.add(buttonList);
	}
}

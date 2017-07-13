package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.BackButton;
import gui.buttons.ExitButton;
import gui.buttons.OpenMenuButton;

public abstract class PauseMenu extends GuiMenu {

	public PauseMenu(Image menuImage) {
		super(menuImage, false);

		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		x = (display.getWidth() / 2) - (menuImage.getWidth() / 2);
		y = (display.getHeight() / 2) - (menuImage.getHeight() / 2);
		VerticalLayout tabs = new VerticalLayout(x + w - 102, y, ImageProcessor.getImage("SkillButton").getHeight() / 2,
				0);
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("InvButton"), 0, 0,
				new OpenMenuButton(new InventoryMenu(ImageProcessor.getImage("InventoryScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("SkillButton"), 0, 0,
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("SkillScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("UpgradesButton"), 0, 0,
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("UpgradesScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("MapButton"), 0, 0,
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("MapScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("SaveButton"), 0, 0,
				new OpenMenuButton(new OptionsMenu(ImageProcessor.getImage("SaveScreen")))));
		menuItems.add(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(x + 30, y + h - 94,
				ImageProcessor.getImage("ExitButton").getHeight() / 2, 20);
		nativeButtons.addMenuItem(new MenuButton(ImageProcessor.getImage("BackButton"), 0, 0, new BackButton()));
		nativeButtons.addMenuItem(new MenuButton(ImageProcessor.getImage("ExitButton"), 0, 0, new ExitButton()));
		menuItems.add(nativeButtons);
	}
}

package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

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
		tabs.addMenuItem(new MenuButton(TypeButton.INVENTORY, ImageProcessor.getImage("InvButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.SKILLS, ImageProcessor.getImage("SkillButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.UPGRADES, ImageProcessor.getImage("UpgradesButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.MAP, ImageProcessor.getImage("MapButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.SAVE, ImageProcessor.getImage("SaveButton"), 0, 0));
		menuItems.add(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(x + 30, y + h - 94,
				ImageProcessor.getImage("ExitButton").getHeight() / 2, 20);
		nativeButtons.addMenuItem(new MenuButton(TypeButton.RESUME, ImageProcessor.getImage("BackButton"), 0, 0));
		nativeButtons.addMenuItem(new MenuButton(TypeButton.MAIN_MENU, ImageProcessor.getImage("ExitButton"), 0, 0));
		menuItems.add(nativeButtons);
	}
}

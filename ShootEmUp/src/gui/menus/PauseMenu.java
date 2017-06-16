package gui.menus;

import display.ImageProcessor;
import display.Image;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public abstract class PauseMenu extends GuiMenu {

	public PauseMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout tabs = new VerticalLayout(922, 0, ImageProcessor.getImage("SkillButton").getHeight() / 2, 0);
		tabs.addMenuItem(new MenuButton(TypeButton.INVENTORY, ImageProcessor.getImage("InvButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.SKILLS, ImageProcessor.getImage("SkillButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.UPGRADES, ImageProcessor.getImage("UpgradesButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.MAP, ImageProcessor.getImage("MapButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.SAVE, ImageProcessor.getImage("SaveButton"), 0, 0));
		this.menuItems.add(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(30, this.display.getHeight() - 94,
				ImageProcessor.getImage("ExitButton").getHeight() / 2, 20);
		nativeButtons.addMenuItem(new MenuButton(TypeButton.RESUME, ImageProcessor.getImage("BackButton"), 0, 0));
		nativeButtons.addMenuItem(new MenuButton(TypeButton.MAIN_MENU, ImageProcessor.getImage("ExitButton"), 0, 0));
		this.menuItems.add(nativeButtons);
	}
}

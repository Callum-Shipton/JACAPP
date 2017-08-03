package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.buttons.ExitButton;
import gui.buttons.OpenMenuButton;
import gui.buttons.ResumeButton;
import gui.layouts.VerticalLayout;

public abstract class PauseMenu extends GuiMenu {

	public PauseMenu(Image menuImage) {
		super(menuImage, false);
	}

	@Override
	public void resetMenu() {
		clearMenu();
		x = (display.getWidth() / 2) - (menuImage.getWidth() / 2);
		y = (display.getHeight() / 2) - (menuImage.getHeight() / 2);
		VerticalLayout tabs = new VerticalLayout(x + w - 102, y, 0);
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("InvButton"),
				new OpenMenuButton(new InventoryMenu(ImageProcessor.getImage("InventoryScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("SkillButton"),
				new OpenMenuButton(new SkillMenu(ImageProcessor.getImage("SkillScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("UpgradesButton"),
				new OpenMenuButton(new UpgradesMenu(ImageProcessor.getImage("UpgradesScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("MapButton"),
				new OpenMenuButton(new MapMenu(ImageProcessor.getImage("MapScreen")))));
		tabs.addMenuItem(new MenuButton(ImageProcessor.getImage("SaveButton"),
				new OpenMenuButton(new SaveMenu(ImageProcessor.getImage("SaveScreen")))));
		addMenuItem(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(x + 30, y + h - 94, 20);
		nativeButtons.addMenuItem(new MenuButton(ImageProcessor.getImage("BackButton"), new ResumeButton()));
		nativeButtons.addMenuItem(new MenuButton(ImageProcessor.getImage("ExitButton"), new ExitButton()));
		addMenuItem(nativeButtons);
	}
}

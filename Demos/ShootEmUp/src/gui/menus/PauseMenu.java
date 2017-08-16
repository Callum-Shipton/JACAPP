package gui.menus;

import display.ArtLoader;
import display.Image;
import gui.GuiMenu;
import gui.Button;
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
		tabs.addMenuItem(new Button(ArtLoader.getImage("InventoryTabButton"),
				new OpenMenuButton(new InventoryMenu(ArtLoader.getImage("InventoryScreen")))));
		tabs.addMenuItem(new Button(ArtLoader.getImage("SkillTabButton"),
				new OpenMenuButton(new SkillMenu(ArtLoader.getImage("SkillScreen")))));
		tabs.addMenuItem(new Button(ArtLoader.getImage("UpgradesTabButton"),
				new OpenMenuButton(new UpgradesMenu(ArtLoader.getImage("UpgradesScreen")))));
		tabs.addMenuItem(new Button(ArtLoader.getImage("MapTabButton"),
				new OpenMenuButton(new MapMenu(ArtLoader.getImage("MapScreen")))));
		tabs.addMenuItem(new Button(ArtLoader.getImage("SaveTabButton"),
				new OpenMenuButton(new SaveMenu(ArtLoader.getImage("SaveScreen")))));
		addMenuItem(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(x + 30, y + h - 94, 20);
		nativeButtons.addMenuItem(new Button(ArtLoader.getImage("BackButton"), new ResumeButton()));
		nativeButtons.addMenuItem(new Button(ArtLoader.getImage("ExitButton"), new ExitButton()));
		addMenuItem(nativeButtons);
	}
}

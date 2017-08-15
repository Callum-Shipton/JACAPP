package gui.menus;

import display.Art;
import display.Image;
import display.ImageProcessor;
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
		tabs.addMenuItem(new Button(Art.getImage("InvButton"),
				new OpenMenuButton(new InventoryMenu(Art.getImage("InventoryScreen")))));
		tabs.addMenuItem(new Button(Art.getImage("SkillButton"),
				new OpenMenuButton(new SkillMenu(Art.getImage("SkillScreen")))));
		tabs.addMenuItem(new Button(Art.getImage("UpgradesButton"),
				new OpenMenuButton(new UpgradesMenu(Art.getImage("UpgradesScreen")))));
		tabs.addMenuItem(new Button(Art.getImage("MapButton"),
				new OpenMenuButton(new MapMenu(Art.getImage("MapScreen")))));
		tabs.addMenuItem(new Button(Art.getImage("SaveButton"),
				new OpenMenuButton(new SaveMenu(Art.getImage("SaveScreen")))));
		addMenuItem(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(x + 30, y + h - 94, 20);
		nativeButtons.addMenuItem(new Button(Art.getImage("BackButton"), new ResumeButton()));
		nativeButtons.addMenuItem(new Button(Art.getImage("ExitButton"), new ExitButton()));
		addMenuItem(nativeButtons);
	}
}

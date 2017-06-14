package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public abstract class PauseMenu extends GuiMenu {

	public PauseMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout tabs = new VerticalLayout(922, 0, Art.getImage("SkillButton").getHeight() / 2, 0);
		tabs.addMenuItem(new MenuButton(TypeButton.INVENTORY, Art.getImage("InvButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.SKILLS, Art.getImage("SkillButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.UPGRADES, Art.getImage("UpgradesButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.MAP, Art.getImage("MapButton"), 0, 0));
		tabs.addMenuItem(new MenuButton(TypeButton.SAVE, Art.getImage("SaveButton"), 0, 0));
		this.menuItems.add(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(30, this.display.getHeight() - 94,
				Art.getImage("ExitButton").getHeight() / 2, 20);
		nativeButtons.addMenuItem(new MenuButton(TypeButton.RESUME, Art.getImage("ResumeButton"), 0, 0));
		nativeButtons.addMenuItem(new MenuButton(TypeButton.MAIN_MENU, Art.getImage("ExitButton"), 0, 0));
		this.menuItems.add(nativeButtons);
	}
}

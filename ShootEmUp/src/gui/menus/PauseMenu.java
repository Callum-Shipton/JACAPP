package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;
import gui.VerticalLayout;

public abstract class PauseMenu extends GuiMenu {

	public PauseMenu(Image MenuImage) {
		super(MenuImage);

		VerticalLayout tabs = new VerticalLayout(922, 0, Art.getImage("SkillButton").getHeight() / 2, 0);
		tabs.addMenuItem(ButtonBuilder.buildButton(TypeButton.INVENTORY, 0, 0));
		tabs.addMenuItem(ButtonBuilder.buildButton(TypeButton.SKILLS, 0, 0));
		tabs.addMenuItem(ButtonBuilder.buildButton(TypeButton.UPGRADES, 0, 0));
		tabs.addMenuItem(ButtonBuilder.buildButton(TypeButton.MAP, 0, 0));
		tabs.addMenuItem(ButtonBuilder.buildButton(TypeButton.SAVE, 0, 0));
		this.menuItems.add(tabs);

		VerticalLayout nativeButtons = new VerticalLayout(30, this.display.getHeight() - 94,
				Art.getImage("ExitButton").getHeight() / 2, 20);
		nativeButtons.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, 0, 0));
		nativeButtons.addMenuItem(ButtonBuilder.buildButton(TypeButton.MAIN_MENU, 0, 0));
		this.menuItems.add(nativeButtons);
	}
}

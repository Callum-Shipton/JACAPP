package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;

public class LevelSelectMenu extends GuiMenu {

	private int level;

	public LevelSelectMenu(Image menuImage, int level) {
		super(menuImage, true);
		this.level = level;
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (ImageProcessor.getImage("Level1Button").getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage("Level1Button").getHeight() / 2, 20);

		switch (level) {
		case 3:
			buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL3, ImageProcessor.getImage("Level3Button"), 0, 0));
		case 2:
			buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL2, ImageProcessor.getImage("Level2Button"), 0, 0));
		case 1:
		default:
			buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL1, ImageProcessor.getImage("Level1Button"), 0, 0));
		}
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, ImageProcessor.getImage("BackButton"), 0, 0));

		this.menuItems.add(buttonList);
	}
}

package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.VerticalLayout;
import gui.buttons.BackButton;
import gui.buttons.SelectLevelButton;

public class LevelSelectMenu extends GuiMenu {

	private int level;

	public LevelSelectMenu(Image menuImage, int level) {
		super(menuImage, true);
		this.level = level;
		resetMenu();
	}

	@Override
	public void resetMenu() {
		final String level1Button = "Level1Button";

		menuItems.clear();
		VerticalLayout buttonList = new VerticalLayout(
				(display.getWidth() / 2) - (ImageProcessor.getImage(level1Button).getWidth() / 2),
				display.getHeight() / 2, ImageProcessor.getImage(level1Button).getHeight() / 2, 20);

		switch (level) {
		case 3: // NOSONAR
			buttonList.addMenuItem(
					new MenuButton(ImageProcessor.getImage("Level3Button"), 0, 0, new SelectLevelButton(3)));
		case 2: // NOSONAR
			buttonList.addMenuItem(
					new MenuButton(ImageProcessor.getImage("Level2Button"), 0, 0, new SelectLevelButton(2)));
		case 1:
		default:
			buttonList
					.addMenuItem(new MenuButton(ImageProcessor.getImage(level1Button), 0, 0, new SelectLevelButton(1)));
		}
		buttonList.addMenuItem(new MenuButton(ImageProcessor.getImage("BackButton"), 0, 0, new BackButton()));

		menuItems.add(buttonList);
	}
}

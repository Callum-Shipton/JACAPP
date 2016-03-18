package gui.menus;

import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class GameOverMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;

    public GameOverMenu(Image menuImage) {
        super(menuImage);
        ShootEmUp.mainMenu = true;
        selectedItem = 0;
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, (ShootEmUp.width / 2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.exitButton.getHeight() / 2), 128,24));
    }
}

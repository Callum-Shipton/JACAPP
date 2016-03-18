package gui.menus;

import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class LoadMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;

    public LoadMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.backButton.getHeight() / 2), 128,24));
    }
}

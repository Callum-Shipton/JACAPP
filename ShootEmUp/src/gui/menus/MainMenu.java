package gui.menus;

import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class MainMenu extends GuiMenu {
	
    public MainMenu(Image menuImage) {
        super(menuImage);
        ShootEmUp.currentLevel = null;
        addButton(new Button(ButtonType.NEW_GAME, Art.newGameButton, (ShootEmUp.width / 2) - (Art.newGameButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.newGameButton.getHeight() * 2)));
        addButton(new Button(ButtonType.LOAD_GAME, Art.loadGameButton, (ShootEmUp.width / 2) - (Art.loadGameButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.loadGameButton.getHeight()));
        addButton(new Button(ButtonType.OPTIONS, Art.optionsButton, (ShootEmUp.width / 2) - (Art.optionsButton.getWidth() / 2), (ShootEmUp.height / 2)));
        addButton(new Button(ButtonType.EXIT, Art.exitButton,  (ShootEmUp.width /  2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.exitButton.getHeight()));
    }
}

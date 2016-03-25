package gui.menus;

import gui.Button;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class MainMenu extends GuiMenu {
	
    public MainMenu(Image menuImage) {
        super(menuImage);
        ShootEmUp.currentLevel = null;
        addButton(ButtonBuilder.buildButton(TypeButton.NEW_GAME, (ShootEmUp.width / 2) - (Art.newGameButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.newGameButton.getHeight() * 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.LOAD_GAME, (ShootEmUp.width / 2) - (Art.loadGameButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.loadGameButton.getHeight()));
        addButton(ButtonBuilder.buildButton(TypeButton.OPTIONS, (ShootEmUp.width / 2) - (Art.optionsButton.getWidth() / 2), (ShootEmUp.height / 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.EXIT,  (ShootEmUp.width /  2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.exitButton.getHeight()));
    }
}

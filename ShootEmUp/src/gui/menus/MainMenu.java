package gui.menus;

import gui.ButtonBuilder;
import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class MainMenu extends GuiMenu {
		
    public MainMenu(Image menuImage) {
        super(menuImage);
        ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.newGameButton.getWidth() / 2), 150, Art.newGameButton.getHeight()/2, 20);
        ShootEmUp.currentLevel = null;
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.NEW_GAME, 0, 0));
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LOAD_GAME, 0, 0));
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.OPTIONS, 0, 0));
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.EXIT, 0, 0));
        menuItems.add(buttonList);
    }
}

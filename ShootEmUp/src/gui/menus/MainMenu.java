package gui.menus;

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
        buttonList.addButton(TypeButton.NEW_GAME);
        buttonList.addButton(TypeButton.LOAD_GAME);
        buttonList.addButton(TypeButton.OPTIONS);
        buttonList.addButton(TypeButton.EXIT);
        menuItems.add(buttonList);
    }
}

package gui.menus;

import main.ShootEmUp;
import gui.Button;
import gui.ButtonType;

import display.Art;
import display.Image;

public class LevelSelectMenu extends GuiMenu {

    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.LEVEL1, Art.level1Button, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - Art.level1Button.getHeight(), 128,24));
        addButton(new Button(ButtonType.LEVEL2, Art.level2Button, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - (Art.level2Button.getHeight() / 4), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) + Art.level1Button.getHeight(), 128,24));
    }
}

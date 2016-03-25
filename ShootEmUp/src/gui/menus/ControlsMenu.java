package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class ControlsMenu extends GuiMenu{

    public ControlsMenu(Image menuImage) {
        super(menuImage);
        addButton(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.backButton.getHeight() / 2)));
    }
}

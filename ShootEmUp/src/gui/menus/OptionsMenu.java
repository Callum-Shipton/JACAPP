package gui.menus;

import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;
import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{
	
    public OptionsMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.CONTROLS, Art.controlsButton, (ShootEmUp.width / 2) - (Art.controlsButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.controlsButton.getHeight()), 128,24));
        addButton(new Button(ButtonType.SOUND, Art.soundButton, (ShootEmUp.width / 2) - (Art.soundButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.soundButton.getHeight() / 4), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.backButton.getHeight()), 128,24));
    }
}

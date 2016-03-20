package gui.menus;

import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class SoundMenu extends GuiMenu{

    public SoundMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.MUTE, Art.muteButton, (ShootEmUp.width / 2) - (Art.muteButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.muteButton.getHeight() / 2)));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.soundButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.soundButton.getHeight() / 2)));
    }
}

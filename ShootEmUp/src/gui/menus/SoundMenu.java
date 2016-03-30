package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class SoundMenu extends GuiMenu{

    public SoundMenu(Image menuImage) {
        super(menuImage);
        menuItems.add(ButtonBuilder.buildButton(TypeButton.MUTE, (ShootEmUp.width / 2) - (Art.muteButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.muteButton.getHeight() / 2)));
        menuItems.add(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.soundButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.soundButton.getHeight() / 2)));
    }
}

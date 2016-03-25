package gui.menus;

import gui.Button;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;
import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{
	
    public OptionsMenu(Image menuImage) {
        super(menuImage);
        addButton(ButtonBuilder.buildButton(TypeButton.CONTROLS, (ShootEmUp.width / 2) - (Art.controlsButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.controlsButton.getHeight())));
        addButton(ButtonBuilder.buildButton(TypeButton.SOUND, (ShootEmUp.width / 2) - (Art.soundButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.soundButton.getHeight() / 4)));
        addButton(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.backButton.getHeight())));
    }
}

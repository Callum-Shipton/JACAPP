package gui.menus;

import gui.ButtonBuilder;
import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;
import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{
	
    public OptionsMenu(Image menuImage) {
        super(menuImage);
        
        ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.controlsButton.getWidth() / 2), 150, Art.controlsButton.getHeight()/2, 20);
        
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.CONTROLS, 0, 0));
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.SOUND, 0, 0));
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, 0, 0));
        
        menuItems.add(buttonList);
    }
}

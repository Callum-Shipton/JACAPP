package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;
import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{
	
    public OptionsMenu(Image menuImage) {
        super(menuImage);
        
        ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.controlsButton.getWidth() / 2), 150, Art.controlsButton.getHeight()/2, 20);
        
        buttonList.addButton(TypeButton.CONTROLS);
        buttonList.addButton(TypeButton.SOUND);
        buttonList.addButton(TypeButton.BACK);
        
        menuItems.add(buttonList);
    }
}

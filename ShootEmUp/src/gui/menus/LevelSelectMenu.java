package gui.menus;

import main.ShootEmUp;
import gui.ButtonBuilder;
import gui.ButtonList;
import gui.TypeButton;

import display.Art;
import display.Image;

public class LevelSelectMenu extends GuiMenu {
	
    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        
        ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), 150, Art.level1Button.getHeight()/2, 20);
        if(ShootEmUp.save == null){
        	  buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL1, 0, 0));
        }
        else {
        	switch(ShootEmUp.save.getLevel()){
        	case 3:
        		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL3, 0, 0));
        	case 2:
        		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL2, 0, 0));
        	case 1:
        		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL1, 0, 0));
        	}
        }
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, 0, 0));
        
        menuItems.add(buttonList);
    }
}

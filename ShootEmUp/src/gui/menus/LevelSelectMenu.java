package gui.menus;

import main.ShootEmUp;

import gui.ButtonList;
import gui.TypeButton;

import display.Art;
import display.Image;

public class LevelSelectMenu extends GuiMenu {
	
    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        
        ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), 150, Art.level1Button.getHeight()/2, 20);
        if(ShootEmUp.save == null){
        	  buttonList.addButton(TypeButton.LEVEL1);
        }
        else {
        	switch(ShootEmUp.save.getLevel()){
        	case 3:
        		buttonList.addButton(TypeButton.LEVEL3);
        	case 2:
        		buttonList.addButton(TypeButton.LEVEL2);
        	case 1:
        		buttonList.addButton(TypeButton.LEVEL1);
        	}
        }
        buttonList.addButton(TypeButton.BACK);
        
        menuItems.add(buttonList);
    }
}

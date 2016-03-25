package gui.menus;

import main.ShootEmUp;
import gui.Button;
import gui.ButtonType;

import display.Art;
import display.Image;

public class LevelSelectMenu extends GuiMenu {

    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        if(ShootEmUp.save == null){
        	  addButton(new Button(ButtonType.LEVEL1, Art.level1Button, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - Art.level1Button.getHeight() * 2));
        }
        else {
        	switch(ShootEmUp.save.getLevel()){
        	case 3:
        		addButton(new Button(ButtonType.LEVEL3, Art.level3Button, (ShootEmUp.width / 2) - (Art.level3Button.getWidth() / 2), (ShootEmUp.height / 2)));
        	case 2:
        		addButton(new Button(ButtonType.LEVEL2, Art.level2Button, (ShootEmUp.width / 2) - (Art.level2Button.getWidth() / 2), (ShootEmUp.height / 2) - (Art.level2Button.getHeight())));
        	case 1:
        		addButton(new Button(ButtonType.LEVEL1, Art.level1Button, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - Art.level1Button.getHeight() * 2));
        	}
        }
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight()));
    }
}

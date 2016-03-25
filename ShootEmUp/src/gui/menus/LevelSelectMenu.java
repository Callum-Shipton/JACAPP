package gui.menus;

import main.ShootEmUp;
import gui.Button;
import gui.ButtonBuilder;
import gui.TypeButton;

import display.Art;
import display.Image;

public class LevelSelectMenu extends GuiMenu {

    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        if(ShootEmUp.save == null){
        	  addButton(ButtonBuilder.buildButton(TypeButton.LEVEL1, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - Art.level1Button.getHeight() * 2));
        }
        else {
        	switch(ShootEmUp.save.getLevel()){
        	case 3:
        		addButton(ButtonBuilder.buildButton(TypeButton.LEVEL3, (ShootEmUp.width / 2) - (Art.level3Button.getWidth() / 2), (ShootEmUp.height / 2)));
        	case 2:
        		addButton(ButtonBuilder.buildButton(TypeButton.LEVEL2, (ShootEmUp.width / 2) - (Art.level2Button.getWidth() / 2), (ShootEmUp.height / 2) - (Art.level2Button.getHeight())));
        	case 1:
        		addButton(ButtonBuilder.buildButton(TypeButton.LEVEL1, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - Art.level1Button.getHeight() * 2));
        	}
        }
        addButton(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight()));
    }
}

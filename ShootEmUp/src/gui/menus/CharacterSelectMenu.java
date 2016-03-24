package gui.menus;

import gui.Button;
import gui.ButtonType;
import gui.Counter2;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class CharacterSelectMenu extends GuiMenu {

	Counter2 warriorLevel;
	Counter2 archerLevel;
	Counter2 mageLevel;
	
    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.WARRIOR, Art.warriorButton, (ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2)));
        addButton(new Button(ButtonType.ARCHER, Art.archerButton, (ShootEmUp.width / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.archerButton.getHeight()));
        addButton(new Button(ButtonType.MAGE, Art.mageButton, (ShootEmUp.width / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2)));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight()));
        
        if(ShootEmUp.save != null){
        	if(ShootEmUp.save.getWarrior() != null){
        		warriorLevel = new Counter2(800, 400);
        	}
        	if(ShootEmUp.save.getArcher() != null){
        		archerLevel = new Counter2(800, 500);
        	}
			if(ShootEmUp.save.getMage() != null){
				mageLevel = new Counter2(800, 600);
			}
        }
    }
    
    public void update(){
    	super.update();
    	
    	if(warriorLevel != null){
    		warriorLevel = new Counter2(800, 400);
    	}
    	if(archerLevel != null){
    		archerLevel = new Counter2(800, 500);
    	}
		if(mageLevel != null){
			mageLevel = new Counter2(800, 600);
		}
    }
    
    public void render(){
    	super.render();
    	
    	if(warriorLevel != null){
    		warriorLevel.render(Art.stat);;
    	}
    	if(archerLevel != null){
    		archerLevel.render(Art.stat);;
    	}
		if(mageLevel != null){
			mageLevel.render(Art.stat);;
		}
    }
}

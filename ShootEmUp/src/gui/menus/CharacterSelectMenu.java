package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
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
        addButton(ButtonBuilder.buildButton(TypeButton.WARRIOR, (ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.ARCHER, (ShootEmUp.width / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.archerButton.getHeight()));
        addButton(ButtonBuilder.buildButton(TypeButton.MAGE, (ShootEmUp.width / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight()));
        
        if(ShootEmUp.save != null){
        	if(ShootEmUp.save.getWarrior() != null){
        		warriorLevel = new Counter2((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2));
        	}
        	if(ShootEmUp.save.getArcher() != null){
        		archerLevel = new Counter2((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2));
        	}
			if(ShootEmUp.save.getMage() != null){
				mageLevel = new Counter2((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2));
			}
        }
    }
    
    public void update(){
    	super.update();
    	
    	if(warriorLevel != null){
    		warriorLevel.update(ShootEmUp.save.getWarrior().getPlayerLevel());
    	}
    	if(archerLevel != null){
    		archerLevel.update(ShootEmUp.save.getArcher().getPlayerLevel());
    	}
		if(mageLevel != null){
			mageLevel.update(ShootEmUp.save.getMage().getPlayerLevel());
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

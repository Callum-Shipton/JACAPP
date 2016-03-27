package gui.menus;

import gui.ButtonBuilder;
import gui.Counter;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class CharacterSelectMenu extends GuiMenu {

	Counter warriorLevel;
	Counter archerLevel;
	Counter mageLevel;
	
    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        addButton(ButtonBuilder.buildButton(TypeButton.WARRIOR, (ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.ARCHER, (ShootEmUp.width / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.archerButton.getHeight()));
        addButton(ButtonBuilder.buildButton(TypeButton.MAGE, (ShootEmUp.width / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight()));
        
        if(ShootEmUp.save != null){
        	if(ShootEmUp.save.getWarrior() != null){
        		warriorLevel = new Counter((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getWarrior().getPlayerLevel());
        	}
        	if(ShootEmUp.save.getArcher() != null){
        		archerLevel = new Counter((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getArcher().getPlayerLevel());
        	}
			if(ShootEmUp.save.getMage() != null){
				mageLevel = new Counter((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getMage().getPlayerLevel());
			}
        }
    }
    
    public void update(){
    	super.update();
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

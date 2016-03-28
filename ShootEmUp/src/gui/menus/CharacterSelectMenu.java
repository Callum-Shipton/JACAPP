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
	Counter battleMageLevel;
	Counter rogueLevel;
	
    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        addButton(ButtonBuilder.buildButton(TypeButton.WARRIOR, (ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.ARCHER, (ShootEmUp.width / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.archerButton.getHeight()));
        addButton(ButtonBuilder.buildButton(TypeButton.MAGE, (ShootEmUp.width / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2)));
        addButton(ButtonBuilder.buildButton(TypeButton.BATTLE_MAGE, (ShootEmUp.width / 2) - (Art.battleMageButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.battleMageButton.getHeight()));
        addButton(ButtonBuilder.buildButton(TypeButton.ROGUE, (ShootEmUp.width / 2) - (Art.rogueButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.rogueButton.getHeight()*2)));
        addButton(ButtonBuilder.buildButton(TypeButton.BACK, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.backButton.getHeight()*3)));
        
        if(ShootEmUp.save != null){
        	if(ShootEmUp.save.getWarrior() != null){
        		warriorLevel = new Counter((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getWarrior().getPlayerLevel(), 0.5f);
        	}
        	if(ShootEmUp.save.getArcher() != null){
        		archerLevel = new Counter((ShootEmUp.width / 2) + (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.archerButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getArcher().getPlayerLevel(), 0.5f);
        	}
			if(ShootEmUp.save.getMage() != null){
				mageLevel = new Counter((ShootEmUp.width / 2) + (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.mageButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getMage().getPlayerLevel(), 0.5f);
			}
			if(ShootEmUp.save.getBattleMage() != null){
				battleMageLevel = new Counter((ShootEmUp.width / 2) + (Art.battleMageButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.battleMageButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getMage().getPlayerLevel(), 0.5f);
			}
			if(ShootEmUp.save.getRogue() != null){
				rogueLevel = new Counter((ShootEmUp.width / 2) + (Art.rogueButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.rogueButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getMage().getPlayerLevel(), 0.5f);
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
		if(battleMageLevel != null){
			battleMageLevel.render(Art.stat);;
		}
		if(rogueLevel != null){
			rogueLevel.render(Art.stat);;
		}
    }
}

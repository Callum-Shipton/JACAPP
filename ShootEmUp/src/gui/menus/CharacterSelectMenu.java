package gui.menus;

import gui.ButtonList;
import gui.Counter;
import gui.TypeButton;
import main.ShootEmUp;
import components.attack.TypeAttack;
import display.Art;
import display.Image;

public class CharacterSelectMenu extends GuiMenu {

	Counter warriorLevel;
	Counter archerLevel;
	Counter mageLevel;
	Counter battleMageLevel;
	Counter rogueLevel;
	ButtonList buttonList;
	
    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), 150, Art.warriorButton.getHeight()/2, 20);
        buttonList.addButton(TypeButton.WARRIOR);
        buttonList.addButton(TypeButton.ARCHER);
        buttonList.addButton(TypeButton.MAGE);
        buttonList.addButton(TypeButton.BATTLE_MAGE);
        buttonList.addButton(TypeButton.ROGUE);
        buttonList.addButton(TypeButton.BACK);  
        if(ShootEmUp.save != null){
        	if(ShootEmUp.save.getCharacter(TypeAttack.WARRIOR) != null){
        		warriorLevel = new Counter((ShootEmUp.width / 2) + (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getCharacter(TypeAttack.WARRIOR).getPlayerLevel(), 0.5f);
        	}
        	if(ShootEmUp.save.getCharacter(TypeAttack.ARCHER) != null){
        		archerLevel = new Counter((ShootEmUp.width / 2) + (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.archerButton.getHeight()), Art.levelIcon, false, ShootEmUp.save.getCharacter(TypeAttack.ARCHER).getPlayerLevel(), 0.5f);
        	}
			if(ShootEmUp.save.getCharacter(TypeAttack.MAGE) != null){
				mageLevel = new Counter((ShootEmUp.width / 2) + (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2), Art.levelIcon, false, ShootEmUp.save.getCharacter(TypeAttack.MAGE).getPlayerLevel(), 0.5f);
			}
			if(ShootEmUp.save.getCharacter(TypeAttack.BATTLE_MAGE) != null){
				battleMageLevel = new Counter((ShootEmUp.width / 2) + (Art.battleMageButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.battleMageButton.getHeight()), Art.levelIcon, false, ShootEmUp.save.getCharacter(TypeAttack.BATTLE_MAGE).getPlayerLevel(), 0.5f);
			}
			if(ShootEmUp.save.getCharacter(TypeAttack.ROGUE) != null){
				rogueLevel = new Counter((ShootEmUp.width / 2) + (Art.rogueButton.getWidth() / 2), (ShootEmUp.height / 2) + (Art.rogueButton.getHeight() * 2), Art.levelIcon, false, ShootEmUp.save.getCharacter(TypeAttack.ROGUE).getPlayerLevel(), 0.5f);
			}
        }
    }
    
    public void update(){
    	super.update();
    	buttonList.update();
    }
    
    public void render(){
    	super.render();
    	buttonList.render();
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

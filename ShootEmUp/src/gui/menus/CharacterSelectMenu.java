package gui.menus;

import gui.ButtonBuilder;
import gui.ButtonList;
import gui.CounterButton;
import gui.TypeButton;
import main.ShootEmUp;
import components.attack.TypeAttack;
import display.Art;
import display.Image;

public class CharacterSelectMenu extends GuiMenu {
	
    public CharacterSelectMenu(Image menuImage) { 	
        super(menuImage);
        
        int warriorLevel = 0;
        int archerLevel = 0;
        int mageLevel = 0;
        int battleMageLevel = 0;
        int rogueLevel = 0;
        
        if(ShootEmUp.save != null){
        	if(ShootEmUp.save.getCharacter(TypeAttack.WARRIOR) != null){
        		warriorLevel = ShootEmUp.save.getCharacter(TypeAttack.WARRIOR).getPlayerLevel();
        	}
        	if(ShootEmUp.save.getCharacter(TypeAttack.ARCHER) != null){
        		archerLevel = ShootEmUp.save.getCharacter(TypeAttack.ARCHER).getPlayerLevel();
        	}
			if(ShootEmUp.save.getCharacter(TypeAttack.MAGE) != null){
				mageLevel = ShootEmUp.save.getCharacter(TypeAttack.MAGE).getPlayerLevel();
			}
			if(ShootEmUp.save.getCharacter(TypeAttack.BATTLE_MAGE) != null){
				battleMageLevel = ShootEmUp.save.getCharacter(TypeAttack.BATTLE_MAGE).getPlayerLevel();
			}
			if(ShootEmUp.save.getCharacter(TypeAttack.ROGUE) != null){
				rogueLevel = ShootEmUp.save.getCharacter(TypeAttack.ROGUE).getPlayerLevel();
			}
        }
        
        ButtonList buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), 150, Art.warriorButton.getHeight()/2, 20);
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.WARRIOR, Art.levelIcon, warriorLevel, 0.5f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ARCHER, Art.levelIcon, archerLevel, 0.5f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MAGE, Art.levelIcon, mageLevel, 0.5f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.BATTLE_MAGE, Art.levelIcon, battleMageLevel, 0.5f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.ROGUE, Art.levelIcon, rogueLevel, 0.5f));
        buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, x, y));
        menuItems.add(buttonList);
    }
}

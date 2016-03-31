package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import gui.Counter;
import gui.CounterButton;
import main.ShootEmUp;
import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class SkillMenu extends PauseMenu {

	Counter skillPoints;
	
    public SkillMenu(Image menuImage) {
        super(menuImage);
        
        ButtonList buttonList = new ButtonList(30, 30, Art.healthButton.getHeight()/2, 20);
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH_REGEN, Art.coin, 1, 1f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH, Art.coin, 1, 1f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA_REGEN, Art.coin, 1, 1f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA, Art.coin, 1, 1f));
		menuItems.add(buttonList);
		skillPoints = new Counter(30.0f, 191.0f, Art.coin, false, ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getLevelPoints(), 1f);
    }
    
    public void update(){
    	super.update();
    	skillPoints.update(((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getLevelPoints());
    }
    
    public void render(){
    	super.render();
    	skillPoints.render(Art.stat);
    }
}

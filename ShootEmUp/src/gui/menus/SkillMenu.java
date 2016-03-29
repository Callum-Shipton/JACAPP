package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import gui.Counter;
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
		buttonList.addButton(TypeButton.HEALTH_REGEN);
		buttonList.addButton(TypeButton.HEALTH);
		buttonList.addButton(TypeButton.MANA_REGEN);
		buttonList.addButton(TypeButton.MANA);
		menuItems.add(buttonList);
		skillPoints = new Counter(30.0f, 166.0f, Art.coin, false, ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getLevelPoints(), 1f);
		menuItems.add(new Counter(160.0f, 35.0f, Art.coin, false, 5, 1f));
		menuItems.add(new Counter(160.0f, 69.0f, Art.coin, false, 5, 1f));
		menuItems.add(new Counter(160.0f, 103.0f, Art.coin, false, 5, 1f));
		menuItems.add(new Counter(160.0f, 137.0f, Art.coin, false, 5, 1f));
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

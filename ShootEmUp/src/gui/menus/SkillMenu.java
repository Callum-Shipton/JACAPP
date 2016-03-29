package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import gui.Counter;
import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class SkillMenu extends PauseMenu {

	Counter skillPoints;
	ButtonList buttonList;
	
    public SkillMenu(Image menuImage) {
        super(menuImage);
        
        buttonList = new ButtonList(30, 30, Art.healthButton.getHeight()/2, 20);
		buttonList.addButton(TypeButton.HEALTH_REGEN);
		buttonList.addButton(TypeButton.HEALTH);
		buttonList.addButton(TypeButton.MANA_REGEN);
		buttonList.addButton(TypeButton.MANA);
		skillPoints = new Counter(30.0f, 166.0f, Art.coin, true, ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getLevelPoints(), 0.5f);
    }
    
    public void update(){
    	super.update();
    	buttonList.render();
    	skillPoints.update(((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getLevelPoints());
    }
    
    public void render(){
    	super.render();
    	buttonList.render();
    	skillPoints.render(Art.stat);
		
    	Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
    	
		Art.stat.draw(Art.numbers, new Vector2(160,35), size, 0.0f, new Vector2(1,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,69), size, 0.0f, new Vector2(1,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,103), size, 0.0f, new Vector2(1,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,137), size, 0.0f, new Vector2(1,1), maxTex);
    }
}

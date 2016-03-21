package gui.menus;

import gui.Button;
import gui.ButtonType;
import gui.Counter;
import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class SkillMenu extends PauseMenu {

	Counter skillPoints;
	
    public SkillMenu(Image menuImage) {
        super(menuImage);
		addButton(new Button(ButtonType.HEALTH_REGEN, Art.healthRegenButton,30, 98));
		addButton(new Button(ButtonType.HEALTH, Art.healthButton,30, 132));
		addButton(new Button(ButtonType.MANA_REGEN, Art.manaRegenButton,30, 166));
		addButton(new Button(ButtonType.MANA, Art.manaButton,30, 200));
		skillPoints = new Counter(30.0f, 234.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true);
    }
    
    public void update(){
    	super.update();
    	
    	skillPoints.update(((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getLevelPoints());
    }
    
    public void render(){
    	super.render();
    	skillPoints.render(Art.stat);
		
    	Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
    	
		Art.stat.draw(Art.numbers, new Vector2(160,103), size, 0.0f, new Vector2(1,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,137), size, 0.0f, new Vector2(1,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,171), size, 0.0f, new Vector2(1,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,205), size, 0.0f, new Vector2(1,1), maxTex);
    }
}

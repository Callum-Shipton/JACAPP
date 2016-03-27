package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import gui.Counter;
import main.ShootEmUp;
import math.Vector2;

import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class UpgradesMenu extends PauseMenu {

	Counter coins;
	
    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        addButton(ButtonBuilder.buildButton(TypeButton.INVENTORY_UPGRADE, 30, 30));
		addButton(ButtonBuilder.buildButton(TypeButton.POTIONS_UPGRADE, 30, 64));
		coins = new Counter(30.0f, 98.0f, Art.coin, true, ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins());
    }
    
    public void update(){
    	super.update();
    	
    	coins.update(((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins());
    }
    
    public void render(){
    	super.render();
    	coins.render(Art.stat);
		
    	Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
    	
		Art.stat.draw(Art.numbers, new Vector2(160,35), size, 0.0f, new Vector2(5,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,69), size, 0.0f, new Vector2(5,1), maxTex);
    }
}

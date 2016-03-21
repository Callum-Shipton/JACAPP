package gui.menus;

import gui.Button;
import gui.ButtonType;
import gui.HudElement;
import main.ShootEmUp;
import math.Vector2;

import java.util.ArrayList;

import components.ComponentType;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class UpgradesMenu extends PauseMenu {

	ArrayList<HudElement> coins = new ArrayList<HudElement>();
	
    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.INVENTORY_UPGRADE, Art.inventoryButton, 30, 98));
		addButton(new Button(ButtonType.POTIONS_UPGRADE, Art.potionsButton, 30, 132));
		coins.add(new HudElement(30.0f, 166.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true));
		coins.add(new HudElement(180.0f, 103.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true));
		coins.add(new HudElement(180.0f, 137.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true));
    }
    
    public void update(){
    	super.update();
    	for(HudElement hudElement : coins){
    		hudElement.update();
    	}
    }
    
    public void render(){
    	super.render();
    	for(HudElement hudElement : coins){
    		hudElement.render(Art.stat);
    	}
    	
    	Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
		
		Art.stat.draw(Art.numbers, new Vector2(160,103), size, 0.0f, new Vector2(5,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,137), size, 0.0f, new Vector2(5,1), maxTex);
		
    	int coins =  ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY)).getCoins();
		if(coins < 10){
			Art.stat.draw(Art.numbers, new Vector2(60,166), size, 0.0f, new Vector2(coins,1), maxTex);
		} else {
			Art.stat.draw(Art.numbers, new Vector2(60,166), size, 0.0f, new Vector2((int) Math.floor(coins / 10),1), maxTex);
			Art.stat.draw(Art.numbers, new Vector2(80,166), size, 0.0f, new Vector2(coins % 10,1), maxTex);
		}
    }
}

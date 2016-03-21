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

public class UpgradesMenu extends PauseMenu {

	Counter coin;
	
    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.INVENTORY_UPGRADE, Art.inventoryButton, 30, 98));
		addButton(new Button(ButtonType.POTIONS_UPGRADE, Art.potionsButton, 30, 132));
		coin = new Counter(30.0f, 166.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true);
    }
    
    public void update(){
    	super.update();
    	
    	coin.update(((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins());
    }
    
    public void render(){
    	super.render();
    	coin.render(Art.stat);
		
    	Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
    	
		Art.stat.draw(Art.numbers, new Vector2(160,103), size, 0.0f, new Vector2(5,1), maxTex);
    	Art.stat.draw(Art.numbers, new Vector2(160,137), size, 0.0f, new Vector2(5,1), maxTex);
    }
}

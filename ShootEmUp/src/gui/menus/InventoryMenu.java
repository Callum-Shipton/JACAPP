package gui.menus;

import gui.Button;
import gui.ButtonType;
import gui.Icon;

import java.util.ArrayList;
import java.util.Iterator;

import object.InventoryItem;
import main.ShootEmUp;
import components.TypeComponent;
import components.inventory.PlayerInventory;
import components.attack.PlayerAttack;
import display.Art;
import display.Image;

public class InventoryMenu extends PauseMenu {
	
	private ArrayList<Button> itemButtons;
	
	private Icon boots;
	private Icon legs;
	private Icon chest;
	private Icon helmet;
	private Icon weapon;
	
	private int row = 0;
	private int column = 0;
	
	private int x;
	private int y;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        itemButtons = new ArrayList<Button>();
        
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getHelmet() != null){    
	        Image helmetArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getHelmet().getInventoryImage();
	        helmet = new Icon(800.0f, 30.0f, helmetArt.getWidth(), helmetArt.getHeight()/2, helmetArt, true);
	    }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getChest() != null){    
	        Image chestArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getChest().getInventoryImage();
	        chest = new Icon(800.0f, 70.0f, chestArt.getWidth(), chestArt.getHeight()/2, chestArt, true);
	    }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getLegs() != null){
	        Image legsArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getLegs().getInventoryImage();
	        legs = new Icon(800.0f, 110.0f, legsArt.getWidth(), legsArt.getHeight()/2, legsArt, true);
        }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getBoots() != null){
	        Image bootsArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getBoots().getInventoryImage();
	        boots = new Icon(800.0f, 150.0f, bootsArt.getWidth(), bootsArt.getHeight()/2, bootsArt, true);
        }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon() != null){   
	        Image weaponArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getInventoryImage();
	        weapon = new Icon(800.0f, 190.0f, weaponArt.getWidth(), weaponArt.getHeight()/2, weaponArt, true);
	    }
        x = 30;
        y = 30;
        
        Iterator<InventoryItem> items = ((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getInventory().iterator();
		while(items.hasNext()){
			InventoryItem item = items.next();
			itemButtons.add(addButton(new Button(ButtonType.OTHER, item.getInventoryImage(), x + ((item.getInventoryImage().getWidth() * row)), y + (((item.getInventoryImage().getHeight()/2) * column)))));
			row++;
			if(row > 10){
				row = 0;
				column++;
			}
		}
    }

    public void render(){
    	super.render();
    	if(boots != null){
    		boots.render(Art.stat);
    	}
    	if(legs != null){
    		legs.render(Art.stat);
    	}
    	if(chest != null){
    		chest.render(Art.stat);
    	}
    	if(helmet != null){
    		helmet.render(Art.stat);
    	}
    	if(weapon != null){
    		weapon.render(Art.stat);
    	}
   	}
    
    
    
    public void update() {
    	super.update();
    	Iterator<Button> Buttons = itemButtons.iterator();
    	Button itemButton;
    	int position = 0;
		while(Buttons.hasNext()){
			itemButton = Buttons.next();
			if(itemButton.hasClicked()){
				((PlayerInventory)(ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).equipItem(position);
				itemButton.postAction();
				removeButton(itemButton);
				Buttons.remove();
				
			}

			position++;
		}
    }
}

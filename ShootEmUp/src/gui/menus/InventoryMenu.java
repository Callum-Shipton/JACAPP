package gui.menus;

import gui.Button;
import gui.ButtonBuilder;
import gui.TypeButton;
import gui.Counter2;
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
	
	private Icon helmet;
	private Icon chest;
	private Icon legs;
	private Icon boots;
	private Icon weapon;
	
	private Counter2 helmetArmour;
	private Counter2 chestArmour;
	private Counter2 legsArmour;
	private Counter2 bootsArmour;
	private Counter2 weaponDamage;
	private Counter2 weaponRange;
	private Counter2 weaponRate;
	private Counter2 weaponCost;
	
	private int row = 0;
	private int column = 0;
	
	private final int inventoryX = 30;
	private final int inventoryY = 30;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        
        addButtons();
    }

    public void addButtons(){
    	
    	addButton(ButtonBuilder.buildButton(TypeButton.RESUME, 30, ShootEmUp.height - 64));
        addButton(ButtonBuilder.buildButton(TypeButton.MAIN_MENU, 30, ShootEmUp.height - 94));
        addButton(ButtonBuilder.buildButton(TypeButton.INVENTORY, 922, 0));
        addButton(ButtonBuilder.buildButton(TypeButton.SKILLS, 922, 204));
        addButton(ButtonBuilder.buildButton(TypeButton.UPGRADES, 922, 102));
        addButton(ButtonBuilder.buildButton(TypeButton.MAP, 922, 306));
        addButton(ButtonBuilder.buildButton(TypeButton.SAVE, 922, 408));
    	
    	if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getHelmet() != null){    
	        Image helmetArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getHelmet().getInventoryImage();
	        helmet = new Icon(600.0f, 30.0f, helmetArt.getWidth(), helmetArt.getHeight()/2, helmetArt, true);
	        helmetArmour = new Counter2(640.0f, 30f);
	    }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getChest() != null){    
	        Image chestArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getChest().getInventoryImage();
	        chest = new Icon(600.0f, 70.0f, chestArt.getWidth(), chestArt.getHeight()/2, chestArt, true);
	        chestArmour = new Counter2(640.0f, 70f);
	    }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getLegs() != null){
	        Image legsArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getLegs().getInventoryImage();
	        legs = new Icon(600.0f, 110.0f, legsArt.getWidth(), legsArt.getHeight()/2, legsArt, true);
	        legsArmour = new Counter2(640.0f, 110.0f);
        }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getBoots() != null){
	        Image bootsArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getBoots().getInventoryImage();
	        boots = new Icon(600.0f, 150.0f, bootsArt.getWidth(), bootsArt.getHeight()/2, bootsArt, true);
	        bootsArmour = new Counter2(640.0f, 150.0f);
        }
	    if(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon() != null){   
	        Image weaponArt =  ((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getInventoryImage();
	        weapon = new Icon(600.0f, 190.0f, weaponArt.getWidth(), weaponArt.getHeight()/2, weaponArt, true);
	        weaponDamage = new Counter2(640.0f, 190.0f);
	        weaponRange = new Counter2(680.0f, 190.0f);
	        weaponRate = new Counter2(720.0f, 190.0f);
	        weaponCost = new Counter2(760.0f, 190.0f);
	    }
        
        itemButtons = new ArrayList<Button>();
    	
        row = 0;
        column = 0;
        
    	itemButtons.clear();
        Iterator<InventoryItem> items = ((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getInventory().iterator();
		while(items.hasNext()){
			InventoryItem item = items.next();
			itemButtons.add(addButton(new Button(TypeButton.OTHER, item.getInventoryImage(), inventoryX + ((item.getInventoryImage().getWidth() * row)), inventoryY + (((item.getInventoryImage().getHeight()/2) * column)))));
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
    		bootsArmour.render(Art.stat);
    	}
    	if(legs != null){
    		legs.render(Art.stat);
    		legsArmour.render(Art.stat);
    	}
    	if(chest != null){
    		chest.render(Art.stat);
    		chestArmour.render(Art.stat);
    	}
    	if(helmet != null){
    		helmet.render(Art.stat);
    		helmetArmour.render(Art.stat);
    	}
    	if(weapon != null){
    		weapon.render(Art.stat);
    		weaponDamage.render(Art.stat);
    		weaponRange.render(Art.stat);
	        weaponRate.render(Art.stat);
	        weaponCost.render(Art.stat);
    	}
   	}
    
    
    
    public void update() {
    	super.update();  	
    	
    	if(boots != null){
    		boots.setI(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getBoots().getInventoryImage());
    		bootsArmour.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getBoots().getDefence());
    	}
    	if(legs != null){
    		legs.setI(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getLegs().getInventoryImage());
    		legsArmour.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getLegs().getDefence());
    	}
    	if(chest != null){
    		chest.setI(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getChest().getInventoryImage());
    		chestArmour.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getChest().getDefence());
    	}
    	if(helmet != null){
    		helmet.setI(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getHelmet().getInventoryImage());
    		helmetArmour.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getHelmet().getDefence());
    	}
    	if(weapon != null){
    		weapon.setI(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getInventoryImage());
    		weaponDamage.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getDamage());
    		weaponRange.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getRange());
	        weaponRate.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getFireRate());
	        weaponCost.update(((PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))).getWeapon().getManaCost());
    	}
    	
    	Iterator<Button> Buttons = itemButtons.iterator();
    	Button itemButton;
    	boolean change = false;
    	int position = 0;
		while(Buttons.hasNext()){
			itemButton = Buttons.next();
			if(itemButton.hasClicked()){
				((PlayerInventory)(ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).equipItem(position);
				itemButton.postAction();
				removeButton(itemButton);
				Buttons.remove();
				change = true;
			}

			position++;
		}
		
		if(change == true){
			buttons.clear();
			addButtons();
		}
    }
}

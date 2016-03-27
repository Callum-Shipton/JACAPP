package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import gui.Counter2;
import gui.Icon;
import gui.Inventory;
import main.ShootEmUp;
import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class InventoryMenu extends PauseMenu {
	
	private PlayerAttack playerAttack;
	
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
	
	private Inventory inventory;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        
        playerAttack = (PlayerAttack)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK);
        
    	addButton(ButtonBuilder.buildButton(TypeButton.RESUME, 30, ShootEmUp.height - 64));
        addButton(ButtonBuilder.buildButton(TypeButton.MAIN_MENU, 30, ShootEmUp.height - 94));
        addButton(ButtonBuilder.buildButton(TypeButton.INVENTORY, 922, 0));
        addButton(ButtonBuilder.buildButton(TypeButton.SKILLS, 922, 204));
        addButton(ButtonBuilder.buildButton(TypeButton.UPGRADES, 922, 102));
        addButton(ButtonBuilder.buildButton(TypeButton.MAP, 922, 306));
        addButton(ButtonBuilder.buildButton(TypeButton.SAVE, 922, 408));
    	
    	if(playerAttack.getHelmet() != null){    
	        Image helmetArt =  playerAttack.getHelmet().getInventoryImage();
	        helmet = new Icon(600.0f, 30.0f, helmetArt.getWidth(), helmetArt.getHeight()/2, helmetArt, true);
	        helmetArmour = new Counter2(640.0f, 30f, playerAttack.getHelmet().getDefence());
	    }
	    if(playerAttack.getChest() != null){    
	        Image chestArt =  playerAttack.getChest().getInventoryImage();
	        chest = new Icon(600.0f, 70.0f, chestArt.getWidth(), chestArt.getHeight()/2, chestArt, true);
	        chestArmour = new Counter2(640.0f, 70f, playerAttack.getChest().getDefence());
	    }
	    if(playerAttack.getLegs() != null){
	        Image legsArt =  playerAttack.getLegs().getInventoryImage();
	        legs = new Icon(600.0f, 110.0f, legsArt.getWidth(), legsArt.getHeight()/2, legsArt, true);
	        legsArmour = new Counter2(640.0f, 110.0f, playerAttack.getLegs().getDefence());
        }
	    if(playerAttack.getBoots() != null){
	        Image bootsArt =  playerAttack.getBoots().getInventoryImage();
	        boots = new Icon(600.0f, 150.0f, bootsArt.getWidth(), bootsArt.getHeight()/2, bootsArt, true);
	        bootsArmour = new Counter2(640.0f, 150.0f, playerAttack.getBoots().getDefence());
        }
	    if(playerAttack.getWeapon() != null){   
	        Image weaponArt =  playerAttack.getWeapon().getInventoryImage();
	        weapon = new Icon(600.0f, 190.0f, weaponArt.getWidth(), weaponArt.getHeight()/2, weaponArt, true);
	        weaponDamage = new Counter2(640.0f, 190.0f, playerAttack.getWeapon().getDamage());
	        weaponRange = new Counter2(680.0f, 190.0f, playerAttack.getWeapon().getRange());
	        weaponRate = new Counter2(720.0f, 190.0f, playerAttack.getWeapon().getFireRate());
	        weaponCost = new Counter2(760.0f, 190.0f, playerAttack.getWeapon().getManaCost());
	    }
	    
	    inventory = new Inventory(((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getInventory());
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
    	
    	inventory.render();
   	}
    
    
    
    public void update() {
    	super.update();  	
    	
    	if(boots != null){
    		boots.setI(playerAttack.getBoots().getInventoryImage());
    		bootsArmour.update(playerAttack.getBoots().getDefence());
    	} else {
    		if(playerAttack.getBoots() != null){
    			Image bootsArt =  playerAttack.getBoots().getInventoryImage();
    	        boots = new Icon(600.0f, 150.0f, bootsArt.getWidth(), bootsArt.getHeight()/2, bootsArt, true);
    	        bootsArmour = new Counter2(640.0f, 150.0f, playerAttack.getBoots().getDefence());
    		}
    	}
    	if(legs != null){
    		legs.setI(playerAttack.getLegs().getInventoryImage());
    		legsArmour.update(playerAttack.getLegs().getDefence());
    	} else {
    		if(playerAttack.getLegs() != null){
    			Image legsArt =  playerAttack.getLegs().getInventoryImage();
    	        legs = new Icon(600.0f, 110.0f, legsArt.getWidth(), legsArt.getHeight()/2, legsArt, true);
    	        legsArmour = new Counter2(640.0f, 110.0f, playerAttack.getLegs().getDefence());
    		}
    	}
    	if(chest != null){
    		chest.setI(playerAttack.getChest().getInventoryImage());
    		chestArmour.update(playerAttack.getChest().getDefence());
    	} else {
    		if(playerAttack.getChest() != null){
    			Image chestArt =  playerAttack.getChest().getInventoryImage();
    	        chest = new Icon(600.0f, 70.0f, chestArt.getWidth(), chestArt.getHeight()/2, chestArt, true);
    	        chestArmour = new Counter2(640.0f, 70.0f, playerAttack.getChest().getDefence());
    		}
    	}
    	if(helmet != null){
    		helmet.setI(playerAttack.getHelmet().getInventoryImage());
    		helmetArmour.update(playerAttack.getHelmet().getDefence());
    	} else {
    		if(playerAttack.getHelmet() != null){
    			Image helmetArt =  playerAttack.getHelmet().getInventoryImage();
    	        helmet = new Icon(600.0f, 30.0f, helmetArt.getWidth(), helmetArt.getHeight()/2, helmetArt, true);
    	        helmetArmour = new Counter2(640.0f, 30.0f, playerAttack.getHelmet().getDefence());
    		}
    	}
    	if(weapon != null){
    		weapon.setI(playerAttack.getWeapon().getInventoryImage());
    		weaponDamage.update(playerAttack.getWeapon().getDamage());
    		weaponRange.update(playerAttack.getWeapon().getRange());
	        weaponRate.update(playerAttack.getWeapon().getFireRate());
	        weaponCost.update(playerAttack.getWeapon().getManaCost());
    	} else {
    		if(playerAttack.getWeapon() != null){   
    	        Image weaponArt =  playerAttack.getWeapon().getInventoryImage();
    	        weapon = new Icon(600.0f, 190.0f, weaponArt.getWidth(), weaponArt.getHeight()/2, weaponArt, true);
    	        weaponDamage = new Counter2(640.0f, 190.0f, playerAttack.getWeapon().getDamage());
    	        weaponRange = new Counter2(680.0f, 190.0f, playerAttack.getWeapon().getRange());
    	        weaponRate = new Counter2(720.0f, 190.0f, playerAttack.getWeapon().getFireRate());
    	        weaponCost = new Counter2(760.0f, 190.0f, playerAttack.getWeapon().getManaCost());
    	    }
    	}
    	
    	inventory.update();
    }
}

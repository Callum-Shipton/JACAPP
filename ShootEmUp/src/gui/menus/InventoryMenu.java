package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import gui.Counter;
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
	
	private Counter helmetArmour;
	private Counter chestArmour;
	private Counter legsArmour;
	private Counter bootsArmour;
	private Counter weaponDamage;
	private Counter weaponRange;
	private Counter weaponRate;
	private Counter weaponCost;
	
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
    	
        helmetIcon();
        chestIcon();
        legsIcon();
        bootsIcon();
        weaponIcon();
	    
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
    		bootsIcon();
    	}
    	if(legs != null){
    		legs.setI(playerAttack.getLegs().getInventoryImage());
    		legsArmour.update(playerAttack.getLegs().getDefence());
    	} else {
    		legsIcon();
    	}
    	if(chest != null){
    		chest.setI(playerAttack.getChest().getInventoryImage());
    		chestArmour.update(playerAttack.getChest().getDefence());
    	} else {
    		chestIcon();
    	}
    	if(helmet != null){
    		helmet.setI(playerAttack.getHelmet().getInventoryImage());
    		helmetArmour.update(playerAttack.getHelmet().getDefence());
    	} else {
    		helmetIcon();
    	}
    	if(weapon != null){
    		weapon.setI(playerAttack.getWeapon().getInventoryImage());
    		weaponDamage.update(playerAttack.getWeapon().getDamage());
    		weaponRange.update(playerAttack.getWeapon().getRange());
	        weaponRate.update(playerAttack.getWeapon().getFireRate());
	        weaponCost.update(playerAttack.getWeapon().getManaCost());
    	} else {
    		weaponIcon();
    	}
    	inventory.update();
    }
    
    public void helmetIcon(){
    	if(playerAttack.getHelmet() != null){    
	        Image helmetArt =  playerAttack.getHelmet().getInventoryImage();
	        helmet = new Icon(560.0f, 30.0f, helmetArt, true, 1f);
	        helmetArmour = new Counter(600.0f, 30f, Art.armourIcon, false, playerAttack.getHelmet().getDefence(), 0.5f);
	    }
    }
    public void chestIcon(){
    	if(playerAttack.getChest() != null){    
	        Image chestArt =  playerAttack.getChest().getInventoryImage();
	        chest = new Icon(560.0f, 70.0f, chestArt, true, 1f);
	        chestArmour = new Counter(600.0f, 70f, Art.armourIcon, false, playerAttack.getChest().getDefence(), 0.5f);
	    }
    }
    public void legsIcon(){
    	if(playerAttack.getLegs() != null){
	        Image legsArt =  playerAttack.getLegs().getInventoryImage();
	        legs = new Icon(560.0f, 110.0f, legsArt, true, 1f);
	        legsArmour = new Counter(600.0f, 110.0f, Art.armourIcon, false, playerAttack.getLegs().getDefence(), 0.5f);
        }
    }
    public void bootsIcon(){
    	if(playerAttack.getBoots() != null){
	        Image bootsArt =  playerAttack.getBoots().getInventoryImage();
	        boots = new Icon(560.0f, 150.0f, bootsArt, true, 1f);
	        bootsArmour = new Counter(600.0f, 150.0f, Art.armourIcon, false, playerAttack.getBoots().getDefence(), 0.5f);
        }
    }
    public void weaponIcon(){
    	if(playerAttack.getWeapon() != null){   
	        weapon = new Icon(560.0f, 190.0f, playerAttack.getWeapon().getInventoryImage(), true, 1f);
	        weaponDamage = new Counter(600.0f, 190.0f, Art.damageIcon, false, playerAttack.getWeapon().getDamage(), 0.5f);
	        weaponRange = new Counter(680.0f, 190.0f, Art.rangeIcon, false, playerAttack.getWeapon().getRange(), 0.5f);
	        weaponRate = new Counter(760.0f, 190.0f, Art.fireRateIcon, false, playerAttack.getWeapon().getFireRate(), 0.5f);
	        weaponCost = new Counter(840.0f, 190.0f, Art.manaCostIcon, false, playerAttack.getWeapon().getManaCost(), 0.5f);
	    }
    }
}

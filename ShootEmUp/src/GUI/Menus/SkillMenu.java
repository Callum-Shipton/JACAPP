package GUI.Menus;

import Components.ComponentType;
import Components.Attack.BaseAttack;
import Components.Attack.PlayerAttack;
import Components.Inventory.BaseInventory;
import Display.Art;
import Display.Image;
import GUI.Button;
import Main.ShootEmUp;

public class SkillMenu extends GuiMenu {
	
	public static boolean saved;
	private Button back;
	private Button exit;
	private Button invButton;
	private Button magicButton;
	private Button mapButton;
	private Button saveButton;
	private Button healthRegenButton;
	private Button healthButton;
	private Button manaRegenButton;
	private Button manaButton;

    public SkillMenu(Image menuImage) {
        super(menuImage);
        back = addButton(new Button(Art.backButton, 30, 30, 128,24));
        exit = addButton(new Button(Art.exitButton, 30, 64, 128,24));
        invButton = addButton(new Button(Art.invButton, 922, 0, 101, 102));
        magicButton = addButton(new Button(Art.magicButton, 922, 102, 101, 102));
        mapButton = addButton(new Button(Art.mapButton, 922, 306, 101, 102));
        saveButton = addButton(new Button(Art.saveButton, 922, 408, 101, 102));
		healthRegenButton = addButton(new Button(Art.healthRegenButton,30, 98,128,24));
		healthButton = addButton(new Button(Art.healthButton,30, 132,128,24));
		manaRegenButton = addButton(new Button(Art.manaRegenButton,30, 166,128,24));
		manaButton = addButton(new Button(Art.manaButton,30, 200,128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(invButton.hasClicked()){
    	    addMenu(new InventoryMenu(Art.invScreen));
    	    invButton.postAction();
    	}
    	if(magicButton.hasClicked()){
    	    addMenu(new MagicMenu(Art.magicScreen));
    	    magicButton.postAction();
    	}
    	if(mapButton.hasClicked()){
    	    addMenu(new MapMenu(Art.mapScreen));
    	    mapButton.postAction();
    	}
    	if(saveButton.hasClicked()){
    	    addMenu(new SaveMenu(Art.saveScreen));
    	    saveButton.postAction();
    	}
    	if(back.hasClicked()){
    		ShootEmUp.menuStack.clear();
        	ShootEmUp.paused = false;
        	back.postAction();
    	}
    	if(exit.hasClicked()){
    		ShootEmUp.menuStack.clear();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	back.postAction();
    	}
    	if ((((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).getCoins() > 0)){
	    	if(healthButton.hasClicked()){
	    		BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
	    		BA.setMaxHealth(BA.getMaxHealth() + 1);
	    		BA.setHealth(BA.getHealth()+1);
	    		healthButton.postAction();
	    		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	    	}
	    	if(healthRegenButton.hasClicked()){
	    		BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
	    		BA.setMaxHealthRegen((int)Math.ceil(BA.getMaxHealthRegen()/2));
	    		healthRegenButton.postAction();
	    		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	    	}
	    	if(manaButton.hasClicked()){
	    		PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
	    		PA.setMaxMana(PA.getMaxMana() + 1);
	    		PA.setMana(PA.getMana() + 1);
	    		manaButton.postAction();
	    		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	    	}
	    	if(manaRegenButton.hasClicked()){
	    		PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
	    		PA.setMaxManaRegen((int)Math.ceil(PA.getMaxManaRegen()/2));
	    		healthButton.postAction();
	    		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	    	}
    	}
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}

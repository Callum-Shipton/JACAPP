package GUI.Menus;

import Display.Art;
import Display.Image;
import GUI.Button;
import Main.ShootEmUp;

public class MagicMenu extends GuiMenu {
	
	public static boolean saved;
	private Button back;
	private Button exit;
	private Button invButton;
	private Button skillButton;
	private Button mapButton;
	private Button saveButton;

    public MagicMenu(Image menuImage) {
        super(menuImage);
        back = addButton(new Button(Art.backButton, 30, 30, 128,24));
        exit = addButton(new Button(Art.exitButton, 30, 64, 128,24));
        invButton = addButton(new Button(Art.invButton, 922, 0, 101, 102));
        skillButton = addButton(new Button(Art.skillButton, 922, 204, 101, 102));
        mapButton = addButton(new Button(Art.mapButton, 922, 306, 101, 102));
        saveButton = addButton(new Button(Art.saveButton, 922, 408, 101, 102));
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
    	if(skillButton.hasClicked()){
    	    addMenu(new SkillMenu(Art.skillScreen));
    	    skillButton.postAction();
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
    		popMenu();
        	ShootEmUp.paused = false;
        	back.postAction();
    	}
    	if(exit.hasClicked()){
    		ShootEmUp.menuStack.clear();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	back.postAction();
    	}
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}

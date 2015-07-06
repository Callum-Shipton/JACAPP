package GUI.Menus;

import Display.Art;
import Display.Image;
import GUI.Button;
import Main.ShootEmUp;

public class InventoryMenu extends GuiMenu {
	
	public static boolean saved;
	private Button back;
	private Button exit;
	private Button skillButton;
	private Button magicButton;
	private Button mapButton;
	private Button saveButton;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        back = addButton(new Button(Art.backButton, 30, 30, 128,24));
        exit = addButton(new Button(Art.exitButton, 30, 64, 128,24));
        magicButton = addButton(new Button(Art.magicButton, 922, 102, 101, 102));
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
    	if(magicButton.hasClicked()){
    	    addMenu(new MagicMenu(Art.magicScreen));
    	    magicButton.postAction();
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
    		ShootEmUp.menuStack.clear();
        	ShootEmUp.paused = false;
        	back.postAction();
    	}
    	if(exit.hasClicked()){
    		ShootEmUp.menuStack.clear();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	exit.postAction();
    	}
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}

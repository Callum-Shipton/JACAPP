package GUI.Menus;

import org.lwjgl.glfw.GLFW;

import Display.Art;
import Display.Image;
import GUI.Button;
import Input.Keyboard;
import Main.ShootEmUp;

public class CharacterSelectMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;
	private Button warrior;
	private Button archer;
	private Button mage;
	private Button back;

    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        warrior = addButton(new Button(Art.warriorButton, (ShootEmUp.WIDTH / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.warriorButton.getHeight() * 2), 128,24));
        archer = addButton(new Button(Art.archerButton, (ShootEmUp.WIDTH / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - Art.archerButton.getHeight(), 128,24));
        mage  = addButton(new Button(Art.mageButton, (ShootEmUp.WIDTH / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2), 128,24));
        back = addButton(new Button(Art.backButton, (ShootEmUp.WIDTH / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + Art.backButton.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(warrior.hasClicked()){
    		ShootEmUp.currentLevel.createPlayer(0);
    		ShootEmUp.paused = false;
    		ShootEmUp.clearMenus();
    		warrior.postAction();
    	}
    	if(archer.hasClicked()){
    		ShootEmUp.currentLevel.createPlayer(1);
    		ShootEmUp.paused = false;
    		ShootEmUp.clearMenus();
    		archer.postAction();
    	}
    	if(mage.hasClicked()){
    		ShootEmUp.currentLevel.createPlayer(2);
    		ShootEmUp.paused = false;
    		ShootEmUp.clearMenus();
    		mage.postAction();
    	}
    	if(back.hasClicked()){
    		popMenu();
        	back.postAction();
    	}
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 2){
    		
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 3){
    		popMenu();
    	}
    	
        else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
            selectedItem++;
            if (selectedItem > 3) {
                selectedItem = 0;
            }
        }
        else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
            selectedItem--;
            if (selectedItem < 0) {
                selectedItem = 3;
            }
        }
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}

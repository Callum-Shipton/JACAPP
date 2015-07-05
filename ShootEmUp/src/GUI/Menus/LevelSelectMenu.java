package GUI.Menus;

import org.lwjgl.glfw.GLFW;

import Display.Art;
import Display.Image;
import GUI.Button;
import Input.Keyboard;
import Level.Level;
import Main.ShootEmUp;

public class LevelSelectMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;
	private Button level1;
	private Button level2;
	private Button back;

    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        level1 = addButton(new Button(Art.level1Button, (ShootEmUp.WIDTH / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - Art.level1Button.getHeight(), 128,24));
        level2 = addButton(new Button(Art.level2Button, (ShootEmUp.WIDTH / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.level2Button.getHeight() / 4), 128,24));
        back = addButton(new Button(Art.backButton, (ShootEmUp.WIDTH / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + Art.level1Button.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(level1.hasClicked()){
    		ShootEmUp.currentLevel = new Level(Art.level1);
    		ShootEmUp.currentLevel.init();
    		addMenu(new CharacterSelectMenu(Art.mainMenuScreen));
    		level1.postAction();
    	}
    	if(level2.hasClicked()){
    		ShootEmUp.currentLevel = new Level(Art.level2);
    		ShootEmUp.currentLevel.init();
    		addMenu(new CharacterSelectMenu(Art.mainMenuScreen));
    		level2.postAction();
    	}
    	if(back.hasClicked()){
    		popMenu();
        	back.postAction();
    	}
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		ShootEmUp.currentLevel = new Level(Art.level1);
    		ShootEmUp.currentLevel.init();
    		ShootEmUp.paused = false;
    		ShootEmUp.clearMenus();
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		ShootEmUp.currentLevel = new Level(Art.level2);
    		ShootEmUp.currentLevel.init();
    		ShootEmUp.paused = false;
    		ShootEmUp.clearMenus();
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
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

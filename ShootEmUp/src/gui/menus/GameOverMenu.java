package gui.menus;

import input.Keyboard;
import gui.Button;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class GameOverMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;
	private Button exit;

    public GameOverMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        exit = addButton(new Button(Art.exitButton, (ShootEmUp.WIDTH / 2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.exitButton.getHeight() / 2), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	
    	if(exit.hasClicked()){
    		popMenu();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	exit.postAction();
    	}
    	//KEYBOARD SELECTION
    	
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
        	popMenu();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    	
    	/*
        else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
            selectedItem++;
            if (selectedItem > 1) {
                selectedItem = 0;
            }
        }
        else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
            selectedItem--;
            if (selectedItem < 0) {
                selectedItem = 1;
            }
        }
        */
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
